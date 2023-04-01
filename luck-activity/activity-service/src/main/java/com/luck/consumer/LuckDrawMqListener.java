package com.luck.consumer;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.luck.constant.CommonEnum;
import com.luck.constant.MqConstant;
import com.luck.domain.resp.LuckDrawResp;
import com.luck.entity.Activity;
import com.luck.entity.Awards;
import com.luck.manager.RedisLuckDrawService;
import com.luck.mapper.ActivityMapper;
import com.luck.mapper.AwardsMapper;
import com.luck.pojo.LuckDrawContext;
import com.luck.pojo.LuckDrawMessage;
import com.luck.resp.R;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description:  抽奖消息消费逻辑
 * @author: pangcheng
 * @time: 2023/2/26 15:57
 */
@Component
@RocketMQMessageListener(consumerGroup = "luckDraw",topic = MqConstant.LUCK_DRAW)
public class LuckDrawMqListener implements RocketMQListener<LuckDrawMessage> {

    private static Logger log = LoggerFactory.getLogger(LuckDrawMqListener.class);

    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private RedisLuckDrawService redisLuckDrawService;

    @Autowired
    private AwardsMapper awardsMapper;

    @Override
    public void onMessage(LuckDrawMessage luckDrawMessage) {

        LuckDrawContext luckDrawContext = new LuckDrawContext();
        try {
            // 获取活动
            Activity activity = activityMapper.selectById(luckDrawMessage.getActivityId());
            luckDrawContext.setActivity(activity);
            // 获取活动对应的奖项
            QueryWrapper queryWrapper  = new QueryWrapper();
            queryWrapper.eq("activity_id", luckDrawMessage.getActivityId());
            List<Awards> awardsList =  awardsMapper.selectList(queryWrapper);
            luckDrawContext.setAwardsList(awardsList);
            luckDrawContext.setUserAuth(luckDrawMessage.getUserAuth());
            // 执行抽奖逻辑
            LuckDrawResp luckDrawResp = redisLuckDrawService.luckDraw(luckDrawContext);
            if(luckDrawResp.getIsDraw() == 1){
                // 设置抽奖结果
                redisLuckDrawService.setLuckDrawResult(R.OK(luckDrawResp),luckDrawMessage.getUserAuth(),luckDrawMessage.getActivityId()) ;
            }else if(luckDrawResp.getIsDraw() == 0){
                redisLuckDrawService.setLuckDrawResult(R.ERROR(CommonEnum.LUCK_DRAW_NOT_DRAW,luckDrawResp),luckDrawMessage.getUserAuth(),luckDrawMessage.getActivityId()) ;
            }else{
                redisLuckDrawService.setLuckDrawResult(R.ERROR(CommonEnum.LUCK_DRAW_REPEAT,luckDrawResp),luckDrawMessage.getUserAuth(),luckDrawMessage.getActivityId()) ;
            }

            // 捕获异常
        }catch (Exception e){
            log.error("抽奖消息消费异常 luckDrawMessage={}",luckDrawMessage,e);
            redisLuckDrawService.setLuckDrawResult(R.ERROR(CommonEnum.FAIL),luckDrawMessage.getUserAuth(),luckDrawMessage.getActivityId());

        }
    }
}
