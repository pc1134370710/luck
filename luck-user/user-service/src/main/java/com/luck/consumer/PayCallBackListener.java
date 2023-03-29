package com.luck.consumer;

import com.luck.constant.MqConstant;
import com.luck.entity.PayPower;
import com.luck.mapper.PayPowerMapper;
import com.luck.model.UserKnowledgePowerMsg;
import com.luck.utils.Snowflake;
import lombok.SneakyThrows;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description: 支付宝回调成功后 将查看权限添加到用户权限中
 *
 *  MQ 设置成 批量消费 implements RocketMQPushConsumerLifecycleListener
 * https://blog.csdn.net/qq_26383975/article/details/125440014
 * @author: pangcheng
 * @create: 2023-03-27 22:27
 **/
@Component
@RocketMQMessageListener(consumerGroup = "userPowerConsumer",topic = MqConstant.USER_POWER)
public class PayCallBackListener implements RocketMQListener<UserKnowledgePowerMsg>{

    private Logger log = LoggerFactory.getLogger(PayCallBackListener.class);

    @Autowired
    private PayPowerMapper payPowerMapper;

    @Override
    public void onMessage(UserKnowledgePowerMsg userKnowledgePowerMsg) {

        log.info("msg= 支付成功， 将商品查看权限 添加给用户 messageExt={}",userKnowledgePowerMsg);
        PayPower payPower = new PayPower();
        payPower.setId(Snowflake.nextId());
        payPower.setUserId(userKnowledgePowerMsg.getUserId());
        payPower.setPkId(userKnowledgePowerMsg.getPkId());
        payPowerMapper.insert(payPower);

    }

//    @Override
//    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
//        //设置每次消息拉取的时间间隔 单位 毫秒
//        defaultMQPushConsumer.setPullInterval(1000);
//        //最小消费线程池数
//        defaultMQPushConsumer.setConsumeThreadMin(1);
//        //最大消费线程池数
//        defaultMQPushConsumer.setConsumeThreadMax(10);
//        //设置消费者单次批量消费的消息数目上限    默认1
//        defaultMQPushConsumer.setConsumeMessageBatchMaxSize(3);
//        //设置每个队列每次拉取的最大消费数
//        defaultMQPushConsumer.setPullBatchSize(16);
//    }
}
