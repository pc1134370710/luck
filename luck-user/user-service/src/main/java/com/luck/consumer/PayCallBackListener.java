package com.luck.consumer;

import com.luck.entity.PayPower;
import com.luck.mapper.PayPowerMapper;
import com.luck.utils.Snowflake;
import lombok.SneakyThrows;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description: 支付宝回调成功后 将查看权限添加到用户权限中
 * @author: pangcheng
 * @create: 2023-03-27 22:27
 **/

@Component
@RocketMQMessageListener(consumerGroup = "orderConsumer",topic = "paySuc")
public class PayCallBackListener implements RocketMQListener<MessageExt> {

    @Autowired
    private PayPowerMapper payPowerMapper;

    @SneakyThrows
    @Override
    public void onMessage(MessageExt messageExt) {
        String pkId = new String(messageExt.getBody(),"utf-8");
        PayPower payPower = new PayPower();
        payPower.setId(Snowflake.nextId());
        payPower.setUserId(1l);
        payPower.setPkId(Long.valueOf(pkId));
        payPowerMapper.insert(payPower);

    }
}
