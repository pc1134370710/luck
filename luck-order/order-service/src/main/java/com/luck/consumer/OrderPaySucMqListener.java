package com.luck.consumer;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.luck.constant.CommonEnum;
import com.luck.constant.MqConstant;
import com.luck.constant.OrderStatusConstant;
import com.luck.entity.PayOrder;
import com.luck.exception.GlobalException;
import com.luck.mapper.PayOrderMapper;
import com.luck.model.UserKnowledgePowerMsg;
import com.luck.resp.R;
import lombok.SneakyThrows;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

/**
 * @description:
 * @author: pangcheng
 * @time: 2023/2/22 14:32
 *  * topic : 主题
 *  * selectorExpression ： 二级分类 ， 不写 默认监听主题下的全部
 *  selectorType : 消息选择器类型
 *      默认值 SelectorType.TAG 根据TAG选择
 *          仅支持表达式格式如：“tag1 || tag2 || tag3”，如果表达式为null或者“*”标识订阅所有消息
 *          SelectorType.SQL92 根据SQL92表达式选择
 *          关键字:
 *          AND, OR, NOT, BETWEEN, IN, TRUE, FALSE, IS, NULL
 *
 *  * consumerGroup 消费组名称
 *   * messageModel :
 *  *   BROADCASTING 代表所有消费者消费同样的消息
 *      CLUSTERING 代表多个消费者轮询消费消息（默认）。
 *  ConsumeMode也有两个选项，
 *         CONCURRENTLY 代表消费端并发消费（默认），消息顺序得不到保证，到底有多少个线程并发消费，取决于线程池的大小。
 *          ORDERLY 代表有序消费，也就是生产者发送的顺序跟消费者消费的顺序一致。
 *
 *  consumeThreadMax 最大线程数  默认值 64
 *  consumeTimeout 超时时间  默认值 30000ms
 *
 *
 */
@Component
@RocketMQMessageListener(consumerGroup = "orderPaySucConsumer",topic = MqConstant.ORDER_PAY_SUCCESS)
public class OrderPaySucMqListener implements RocketMQListener<MessageExt> {

    private Logger log =  LoggerFactory.getLogger(OrderPaySucMqListener.class);

    @Autowired
    private PayOrderMapper payOrderMapper;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    /**
     *   只要没有异常出现，那么就会消费成功，有异常出现了就重新进行发送
     *      失败 重试 时间越来越久 10s 30s 60s 1m 2m 10m
     *
     *    DefaultMessageListenerOrderly.class
     *    https://blog.csdn.net/qq_38154587/article/details/127729803
     *
     * @param message
     */
    @SneakyThrows // @SneakyThrows注解是由lombok封装的,为代码生成一个try…catch块,并把异常向上抛出来
    @Override
    public void onMessage(MessageExt message) {
        System.out.println(LocalDateTime.now());
        log.info("msg = 支付宝支付回调成功，MQ进行消费, message={}",message);

        // 根据订单id 查找订单
        String orderId = new String(message.getBody(),"utf-8");
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("order_id",orderId);
        PayOrder order = payOrderMapper.selectOne(queryWrapper);


        UserKnowledgePowerMsg userKnowledgePowerMsg = new UserKnowledgePowerMsg();
        userKnowledgePowerMsg.setPkId(order.getPkId());
        userKnowledgePowerMsg.setUserId(order.getUserId());

        Message<UserKnowledgePowerMsg> userPowerMsg = MessageBuilder.withPayload(userKnowledgePowerMsg)
                .setHeader(RocketMQHeaders.KEYS, order.getUserId())
                .build();
        // 将付费知识id  添加到用户的访问权限中
        // 发送至mq 中
        SendResult sendResult = rocketMQTemplate.syncSend(MqConstant.USER_POWER, userPowerMsg);
        if(!sendResult.getSendStatus().name().equals(SendStatus.SEND_OK.name())){
            log.warn("cmd = OrderPaySucMqListener | msg = 发送mq消息失败 sendResult={}",sendResult);
            throw new GlobalException(R.ERROR(CommonEnum.ORDER_SEND_MSG_FAIL));
            // 退款？
        }
        log.info("消息发送至 {} 队列中，等待消费",MqConstant.USER_POWER);
        // 结束
    }
}
