package com.luck.manager;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.RandomUtil;
import com.luck.constant.CommonEnum;
import com.luck.constant.MqConstant;
import com.luck.domain.resp.LuckDrawResp;
import com.luck.entity.Activity;
import com.luck.entity.Awards;
import com.luck.entity.LuckDrawContext;
import com.luck.entity.RaffleRecord;
import com.luck.exception.GlobalException;
import com.luck.mapper.RaffleRecordMapper;
import com.luck.model.UserAuth;
import com.luck.model.UserKnowledgePowerMsg;
import com.luck.resp.R;
import com.luck.utils.Snowflake;
import com.luck.utils.UserInfoThreadLocal;
import org.apache.commons.codec.CharEncoding;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.util.ConditionalOnBootstrapEnabled;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.util.*;

/**
 * @description:
 * @author: pangcheng
 * @create: 2023-03-28 20:46
 **/

@Service
public class RedisLuckDrawService  {

    private static Logger log = LoggerFactory.getLogger(RedisLuckDrawService.class);
    private static String stockDeductionLua;
    private static String stockRollbackLua;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
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
     *  抽奖算法
     * @param luckDraw 抽奖对象
     * @return
     */
    public  LuckDrawResp luckDraw(LuckDrawContext luckDraw) {

        LuckDrawResp luckDrawResp = new LuckDrawResp();
        // 校验用户是否已经重复抽奖了
        // 埋点 ,校验抽奖规则， 后续加入 用户满一定积分才可以抽奖，或者不允许重复抽奖 等
        verifyLuckDrawRule(luckDraw.getActivity());

        // 剔除奖项库存 不足的项，就是已经被抽完了
        // 概率为负数或者库存为0的数据，概率设置为0
        verifyAwardStock(luckDraw.getAwardsList());

        // 抽奖前置流程
//        afterDrawHandle();
        // 执行抽奖
        Awards awards = luckDrawCore(luckDraw);

        luckDrawResp.setActivityId(luckDraw.getActivity().getId());
        luckDrawResp.setActivityName(luckDraw.getActivity().getActivityName());
        // 开始扣减库存, 返回实际奖项
        boolean beforeLuckDraw = beforeLuckDraw(luckDraw.getActivity(), awards);
        if(!beforeLuckDraw){
            // 扣减库存失败 返回未中奖
            luckDrawResp.setAwardsName("很遗憾，未中奖");
            return luckDrawResp;
        }

        luckDrawResp.setAwardsName(awards.getAwardsName());
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
            1. 计算权重总结果 sum
            2. 将所有权重 从小到大排序 list
            3. 生成一个 0-sum 之间的随机数 rand
            4. 二分在 list 集合中 找到第一个大于或者等于 随机数的rand 值
            5. 该值就是 抽奖结果
            https://www.cnblogs.com/createwell/p/13527165.html
         */

//        获取该抽奖活动对应的奖项
        Integer weightSum = 0;
        // 下标要从1 开始 所以
        Integer[] weight = new Integer[luckDraw.getAwardsList().size()];
        List<Awards> awardsList = luckDraw.getAwardsList();
        // 将商品按照概率 从小到大排序
        Collections.sort(awardsList, new Comparator<Awards>() {
            @Override
            public int compare(Awards o1, Awards o2) {
                return o1.getProbability()<o2.getProbability()?1:-1;
            }
        });
        for(int i=0; i<awardsList.size() ; i++){
            weightSum+=awardsList.get(i).getProbability();
            weight[i]=awardsList.get(i).getProbability();
        }
        // +1 确保不会选择到 概率为0 的 奖项
        Integer randomInt = RandomUtil.randomInt(weightSum)+1;
        // weight中 二分找到 第一个大于或者等于 randomInt 的下标就是中奖项
        int l =0,r = weight.length;
        while (l<r){
            int mid = l+r >>1;
            if(randomInt< weight[mid]){
                r=mid;
            }else{
                l =mid+1;
            }
        }
        // l 就是最终结果，第一个大于或者等于  randomInt 的下标
        return awardsList.get(l);
    }

    /**
     * 抽奖后置流程
     * @param activity
     * @param awards
     * @return
     */
    private boolean beforeLuckDraw(Activity activity, Awards awards) {
        // 开始扣减奖品库存
        Integer stockDeduct = invokeStockDeductionLua( activity,  awards);
        if(stockDeduct == 0){
            // 扣减库存失败， 奖品库存为空， 返回未中奖
            return false;
        }
        // 使用编程式事务
       return transactionTemplate.execute(status ->{
            try {
                // 插入抽奖记录
                addRecord(activity, awards);
                // 最好是插入 不可见的抽奖记录， 等待抽奖流程都完成后在进行修改状态，并且将奖品发放给用户
                // 发送奖品 MQ 给 用户
                UserKnowledgePowerMsg userKnowledgePowerMsg = new UserKnowledgePowerMsg();
                UserAuth userAuth = UserInfoThreadLocal.get();
                userKnowledgePowerMsg.setUserId(userAuth.getUserId());
                userKnowledgePowerMsg.setPkId(awards.getPkId());
                SendResult sendResult = rocketMQTemplate.syncSend(MqConstant.USER_POWER, userKnowledgePowerMsg);
                if(!SendStatus.SEND_OK.name().equals(sendResult)){
                    throw new GlobalException(R.ERROR(CommonEnum.FAIL));
                }
            }catch (Exception e){
                // 回滚库存
                log.error("cmd ",e);
                invokeStockRollbackLua(activity,  awards);
                status.setRollbackOnly();
                return false;
            }
            return true;
        });

    }

    /**
     * 检验 商品库存
     * @param awardsList
     */
    private void verifyAwardStock(List<Awards> awardsList) {
        for (Awards awards : awardsList){
            // 如果是 真实的奖项， 并且库存已经为0 了 ， 则将概率设置成0
            if(awards.getPkId()!=null && awards.getStock()<=0){
                awards.setProbability(0);
            }
        }

    }

    /**
     * 检查用户校验规则
     * @param activity
     * @return true 检查通过
     *  false：不通过
     */
    private void verifyLuckDrawRule(Activity activity) {
        UserAuth userAuth = UserInfoThreadLocal.get();
        // 判断用户是否已经抽奖了

    }

    /**
     * 插入抽奖记录
     * @param activity
     * @param awards
     */
    private String addRecord(Activity activity, Awards awards){
        // 插入抽奖记录
        RaffleRecord raffleRecord = new RaffleRecord();
        String id = Snowflake.nextId();
        raffleRecord.setId(id);
        raffleRecord.setAdId(awards.getId());
        raffleRecord.setAyId(activity.getId());
        raffleRecord.setAwardsName(awards.getAwardsName());
        raffleRecord.setActivityName(activity.getActivityName());
        raffleRecord.setStatus(1);
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
        String key = activity.getId()+awards.getId();
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
        String key = activity.getId()+awards.getId();
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
