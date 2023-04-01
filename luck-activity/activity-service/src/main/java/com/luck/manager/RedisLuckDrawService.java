package com.luck.manager;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.luck.constant.CommonEnum;
import com.luck.constant.LuckDrawConstant;
import com.luck.constant.MqConstant;
import com.luck.domain.resp.LuckDrawResp;
import com.luck.entity.Activity;
import com.luck.entity.Awards;
import com.luck.pojo.LuckDrawContext;
import com.luck.entity.RaffleRecord;
import com.luck.exception.GlobalException;
import com.luck.mapper.RaffleRecordMapper;
import com.luck.model.UserAuth;
import com.luck.pojo.LuckDrawStockMessage;
import com.luck.resp.R;
import com.luck.utils.RedisUtils;
import com.luck.utils.Snowflake;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @description:
 * @author: pangcheng
 * @create: 2023-02-28 20:46
 **/

@Service
public class RedisLuckDrawService  {

    private static Logger log = LoggerFactory.getLogger(RedisLuckDrawService.class);
    private static String stockDeductionLua;
    private static String stockRollbackLua;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private RaffleRecordMapper raffleRecordMapper;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    private TransactionTemplate transactionTemplate;

    static {
        stockDeductionLua = read("lua/stock_deduction.lua");
        stockRollbackLua = read("lua/stock_rollback.lua");
    }


    /**
     * 设置抽奖结果
     * @param r 抽奖结果
     * @param activityId 抽奖活动id
     * @param userAuth 抽奖用户信息
     * @return
     */
    public void setLuckDrawResult(R r,UserAuth userAuth,String activityId){
        String key = LuckDrawConstant.getCacheResultKey(activityId,userAuth.getUserId());
        redisUtils.hmSet(LuckDrawConstant.CACHE_KEY_LUCK_DRAW_RESULT,key,r);
    }


    /**
     * 获取抽奖结果
     * @param activityId 抽奖活动id
     * @param userAuth 抽奖用户信息
     * @return
     */
    public R<LuckDrawResp> getLuckDrawResult(String activityId,UserAuth userAuth){
        // 从redis 中获取抽奖结果
        String key = LuckDrawConstant.getCacheResultKey(activityId,userAuth.getUserId());
        R<LuckDrawResp> respR = (R<LuckDrawResp>) redisUtils.hmGet(LuckDrawConstant.CACHE_KEY_LUCK_DRAW_RESULT, key);
        if(respR==null){
            // 暂无抽奖结果
            return R.ERROR(CommonEnum.LUCK_DRAW_NOT_RESULT);
        }
        // 获取完结果后 将其从redis 中删除
        redisUtils.hmDel(LuckDrawConstant.CACHE_KEY_LUCK_DRAW_RESULT,key);
        return respR;
    }

    /**
     *  抽奖算法
     * @param luckDraw 抽奖对象
     * @return
     */
    public  LuckDrawResp luckDraw(LuckDrawContext luckDraw) {

        LuckDrawResp luckDrawResp = new LuckDrawResp();
        luckDrawResp.setIsDraw(1);
        luckDrawResp.setActivityId(luckDraw.getActivity().getId());
        luckDrawResp.setAwardsName(luckDraw.getActivity().getActivityName());
        // 校验用户是否已经重复抽奖了
        // 埋点 ,校验抽奖规则， 后续加入 用户满一定积分才可以抽奖，或者不允许重复抽奖 等
        if(!verifyLuckDrawRule(luckDraw)){
            luckDrawResp.setAwardsName("很遗憾，未中奖");
            luckDrawResp.setIsDraw(2);
            return luckDrawResp;
        }

        // 剔除奖项库存 不足的项，就是已经被抽完了
        // 概率为负数或者库存为0的数据，概率设置为0
        verifyAwardStock(luckDraw.getActivity(),luckDraw.getAwardsList());

        // 抽奖前置流程
//        afterDrawHandle();
        // 执行抽奖
        Awards awards = luckDrawCore(luckDraw);
        // 开始扣减库存, 返回实际奖项
        boolean beforeLuckDraw = beforeLuckDraw(luckDraw, awards);
        if(!beforeLuckDraw){
            // 扣减库存失败 返回未中奖
            luckDrawResp.setAwardsName("很遗憾，未中奖");
            luckDrawResp.setIsDraw(0);
            return luckDrawResp;
        }

        luckDrawResp.setAwardsName(awards.getAwardsName());
        // 释放redis 正在抽奖标志
        String key =  LuckDrawConstant.getCacheRepeatDrawKey(luckDrawResp.getActivityId(), luckDraw.getUserAuth().getUserId());
        redisTemplate.delete(key);
        return luckDrawResp;
    }


    /**
     * 抽奖算法核心部分
     * @param luckDraw
     * @return 抽奖结果奖品
     */
    private Awards luckDrawCore(LuckDrawContext luckDraw){
        // 抽奖算法
        // 洗牌算法 水塘抽样 算法
        // 权重随机抽取
        /*
            1. 每个奖品的 中奖权重
            2. 将所有权重 从小到大排序 list
            3. 生成一个 0-sum 之间的随机数 rand
            4. 二分在 list 集合中 找到第一个大于或者等于 随机数的rand 值
            5. 该值就是 抽奖结果
            https://www.cnblogs.com/createwell/p/13527165.html
         */

//        获取该抽奖活动对应的奖项
        // 构建前缀和 下标要从1 开始 所以
        int[] weight = new int[luckDraw.getAwardsList().size()+1];
        List<Awards> awardsList = luckDraw.getAwardsList();
        // 将商品按照概率 从小到大排序
        Collections.sort(awardsList, new Comparator<Awards>() {
            @Override
            public int compare(Awards o1, Awards o2) {
                return o1.getProbability()>o2.getProbability()?1:-1;
            }
        });
        for(int i=1; i<=awardsList.size() ; i++){
            weight[i]=weight[i-1]+awardsList.get(i-1).getProbability();
        }
        // +1 确保不会选择到 概率为0 的 奖项
        // randoInt -> [1 -  weight[weight.length-1] ]
        // random.nextInt(max - min) + min;   生成区间 min - max 之间的数
        Integer randomInt = ThreadLocalRandom.current().nextInt(weight[weight.length-1])+1;
        // weight中 二分找到 第一个大于或者等于 randomInt 的下标就是中奖项
        int l =0,r = weight.length;
        while (l<r){
            int mid = l+r >>1;
           if(randomInt<= weight[mid]){
                r=mid;
            }else{
                l =mid+1;
            }
        }
        // l 就是最终结果，第一个大于或者等于  randomInt 的下标
        return awardsList.get(l-1);
    }

    /**
     * 抽奖后置流程
     * @param luckDraw
     * @param awards
     * @return
     */
    private boolean beforeLuckDraw(LuckDrawContext luckDraw, Awards awards) {
        Activity activity = luckDraw.getActivity();
        // 开始扣减奖品库存
        Integer stockDeduct = invokeStockDeductionLua( activity,  awards);
        if(stockDeduct == 0){
            // 扣减库存失败， 奖品库存为空， 返回未中奖
            return false;
        }
        UserAuth userAuth = luckDraw.getUserAuth();
        // 使用编程式事务
        transactionTemplate.execute(status ->{
            try {
                // 插入抽奖记录
                String recordId = addRecord(activity, awards, userAuth);
                // 插入 正在抽奖中的抽奖记录， 等待抽奖流程都完成后在进行修改状态，并且修改实际库存数量

                // 并且将奖品发放给用户
                // 不是谢谢惠顾
                if(awards.getPkId()!=null){
                    LuckDrawStockMessage luckDrawStockMessage = new LuckDrawStockMessage();
                    luckDrawStockMessage.setActivityId(activity.getId());
                    luckDrawStockMessage.setRecordId(recordId);
                    luckDrawStockMessage.setUserId(userAuth.getUserId());
                    luckDrawStockMessage.setPkId(awards.getPkId());
                    luckDrawStockMessage.setAwardsId(awards.getId());
                    SendResult sendResult = rocketMQTemplate.syncSend(MqConstant.LUCK_DRAW_STOCK, luckDrawStockMessage);
                    log.info("发送消息给目前 ：{}",sendResult );
                    if(!SendStatus.SEND_OK.name().equals(sendResult.getSendStatus().name())){
                        throw new GlobalException(R.ERROR(CommonEnum.FAIL));
                    }
                }

            }catch (Exception e){
                // 回滚库存
                log.error("cmd =beforeLuckDraw | msg = 插入不可见抽奖记录失败或者发送mq失败 | userAuth={} ，activity={}，awards={} ",
                        userAuth,activity,awards,e);
                invokeStockRollbackLua(activity,  awards);
                status.setRollbackOnly();
                return false;
            }
            return true;
        });


       return  true;

    }

    /**
     * 检验 商品库存
     * @param activity
     * @param awardsList
     */
    private void verifyAwardStock(Activity activity,List<Awards> awardsList) {
        for (Awards awards : awardsList){
            // 如果是 真实的奖项， 并且库存已经为0 了 ， 则将概率设置成0
            if(awards.getPkId()!=null && awards.getStock()<=0){
                awards.setProbability(0);
            }
        }

    }

    /**
     * 检查用户校验规则
     * @param luckDraw
     * @return true 检查通过
     *  false：不通过
     */
    private boolean verifyLuckDrawRule(LuckDrawContext luckDraw) {
        UserAuth userAuth = luckDraw.getUserAuth();
        Activity activity = luckDraw.getActivity();
        // 判断用户是否已经抽奖了

        // todo 先 判断 redis中是否有标志位标志该用户正在抽奖
        //  如果没有， 则设置正在抽奖， 并且查询数据库是否有抽奖记录
        //  如果有抽奖记录，删除标志位， 并且返回 不能重复抽奖的提示
        //  抽奖成功后 将该标志位删除即可
        /*
            setnx 和 setex 的区别

            setnx key value  将 key 的值设为 value ，当且仅当 key 不存在。 若给定的 key 已经存在，则 SETNX 不做任何动作。  设置成功，返回 1 。设置失败，返回 0 。
            setex key seconds value  将值 value 关联到 key ，并将 key 的生存时间设为 seconds (以秒为单位)。
                如果 key 已经存在， SETEX 命令将覆写旧值。
            setIfAbsent： 使用的是 setnx

         */

        // 如果key 不存在， 则设置key 的值为value 返回 true
        // 如果key 已经存在， 会返回false
        String key =  LuckDrawConstant.getCacheRepeatDrawKey(activity.getId(), userAuth.getUserId());
        Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent(key, activity.getId());
        if(!aBoolean){
           // 设置失败
            // 不可重复抽奖
            log.warn("cmd = verifyLuckDrawRule |msg = 不可重复抽奖 | 用户:{} , 活动:{}",userAuth.getUserName(),activity.getActivityName());
//            throw new GlobalException(R.ERROR(CommonEnum.LUCK_DRAW_REPEAT));
            return false;
        }
        // 查询数据库是否有抽奖记录
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("ay_id",activity.getId());
        queryWrapper.eq("user_id",userAuth.getUserId());
        RaffleRecord raffleRecord = raffleRecordMapper.selectOne(queryWrapper);
        if(raffleRecord !=null){
            //  如果有抽奖记录，删除标志位
            redisTemplate.delete(key);
            // 不可重复抽奖
            log.warn("cmd = verifyLuckDrawRule |msg = 不可重复抽奖 | 用户:{} , 活动:{}",userAuth.getUserName(),activity.getActivityName());
//            throw new GlobalException(R.ERROR(CommonEnum.LUCK_DRAW_REPEAT));
            return false;
        }
        return true;

    }

    /**
     * 插入抽奖记录
     * @param activity
     * @param awards
     */
    private String addRecord(Activity activity, Awards awards, UserAuth userAuth){
        // 插入抽奖记录
        RaffleRecord raffleRecord = new RaffleRecord();
        String id = Snowflake.nextId();
        raffleRecord.setId(id);
        raffleRecord.setUserId(userAuth.getUserId());
        raffleRecord.setAdId(awards.getId());
        raffleRecord.setAyId(activity.getId());
        raffleRecord.setAwardsName(awards.getAwardsName());
        raffleRecord.setActivityName(activity.getActivityName());
        raffleRecord.setStatus(0);
        raffleRecord.setCreateTime(LocalDateTime.now());
        raffleRecord.setIsDraw(awards.getPkId()==null?0:1);
        raffleRecordMapper.insert(raffleRecord);
        return id;
    }

    /**
     * 扣减库存
     * @param activity 抽奖活动
     * @param awards 抽奖算法获取的奖项
     * @return
     */
    private Integer invokeStockDeductionLua(Activity activity, Awards awards) {
        // 谢谢参与商品 不用扣减库存
        if(awards.getPkId() == null){
            return 1;
        }
        String key =LuckDrawConstant.getLuckDrawStock(activity.getId(),awards.getId());
        /**
         * stockDeductionLua: lua 脚本
         * Long.class: 返回执行后的库存值类型
         */
        RedisScript<Long> redisScript = new DefaultRedisScript<>(stockDeductionLua, Long.class);
        Long execute = redisTemplate.opsForValue().getOperations().execute(
                redisScript,
                Arrays.asList(key));
        if (Objects.isNull(execute) || execute == -1) {
            return 0;
        }
        return 1;
    }


    /**
     * 库存回滚
     * @return
     */
    private Integer invokeStockRollbackLua(Activity activity, Awards awards) {
        // 谢谢参与商品 不用扣减库存
        if(awards.getPkId() == null){
            return 1;
        }
        String key =LuckDrawConstant.getLuckDrawStock(activity.getId(),awards.getId());
        /**
         * stockDeductionLua: lua 脚本
         * Long.class: 返回执行后的库存值类型
         */
        RedisScript<Long> redisScript = new DefaultRedisScript<>(stockRollbackLua, Long.class);

        Long execute = redisTemplate.opsForValue().getOperations().execute(
                redisScript,
                Arrays.asList(key));
        if (Objects.isNull(execute) || execute < 0) {
            return 0;
        }
        return 1;
    }
    /**
     * 读取lua 脚本
     * @param fileName
     * @return
     */
    private static String read(String fileName) {
        String val = "";
        try {
            val = IoUtil.read(new FileInputStream(FileUtil.file(fileName)), Charset.forName("utf-8"));
        } catch (Exception e) {
            //错误处理
            log.error("加载 lua 脚本失败",e);
            throw new GlobalException(R.ERROR(CommonEnum.FAIL));
        }
        return val;
    }
}
