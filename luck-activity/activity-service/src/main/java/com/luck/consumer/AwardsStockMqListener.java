package com.luck.consumer;

import com.luck.constant.CommonEnum;
import com.luck.constant.MqConstant;
import com.luck.entity.RaffleRecord;
import com.luck.exception.GlobalException;
import com.luck.manager.RedisLuckDrawService;
import com.luck.mapper.AwardsMapper;
import com.luck.mapper.RaffleRecordMapper;
import com.luck.model.UserKnowledgePowerMsg;
import com.luck.pojo.LuckDrawStockMessage;
import com.luck.resp.R;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @description: 减少奖品库存的mq 消费组
 * @author: pangcheng
 * @time: 2023/2/26 15:57
 */
@Component
@RocketMQMessageListener(consumerGroup = "awardsStock",topic = MqConstant.LUCK_DRAW_STOCK)
public class AwardsStockMqListener implements RocketMQListener<LuckDrawStockMessage> {
    private static Logger log = LoggerFactory.getLogger(AwardsStockMqListener.class);
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Autowired
    private AwardsMapper awardsMapper;
    @Autowired
    private RaffleRecordMapper raffleRecordMapper;

    @Autowired
    private TransactionTemplate transactionTemplate;


    @Override
    public void onMessage(LuckDrawStockMessage luckDrawStockMessage) {

        log.info("消费开始扣减奖品库存，luckDrawStockMessage={}",luckDrawStockMessage);
        transactionTemplate.execute(status -> {
            // 扣减库存
            awardsMapper.updateStock(luckDrawStockMessage.getAwardsId(),-1);
            // 将记录改成可见
            RaffleRecord raffleRecord = new RaffleRecord();
            raffleRecord.setId(luckDrawStockMessage.getRecordId());
            raffleRecord.setStatus(1);
            raffleRecordMapper.updateById(raffleRecord);

            // 发送奖品 MQ 给 用户
            UserKnowledgePowerMsg userKnowledgePowerMsg = new UserKnowledgePowerMsg();
            userKnowledgePowerMsg.setUserId(luckDrawStockMessage.getUserId());
            userKnowledgePowerMsg.setPkId(luckDrawStockMessage.getPkId());
            SendResult sendResult = rocketMQTemplate.syncSend(MqConstant.USER_POWER, userKnowledgePowerMsg);
            log.info("发送消息给用户添加付费知识的查看权限 ：{}",sendResult );
            if(!SendStatus.SEND_OK.name().equals(sendResult.getSendStatus().name())){
                throw new GlobalException(R.ERROR(CommonEnum.FAIL));
            }
            return true;
        });

    }
}
