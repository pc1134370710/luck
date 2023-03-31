package com.luck.consumer;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.luck.constant.MqConstant;
import com.luck.constant.OrderStatusConstant;
import com.luck.entity.PayOrder;
import com.luck.mapper.PayOrderMapper;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.ArrayList;

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
@RocketMQMessageListener(consumerGroup = "orderStatusConsumer",topic = MqConstant.ORDER_STATUS)
public class OrderStatusMqListener implements RocketMQListener<MessageExt> {


    private static Logger log = LoggerFactory.getLogger(OrderStatusMqListener.class);

    @Autowired
    private PayOrderMapper payOrderMapper;


    /**
     *   只要没有异常出现，那么就会消费成功，有异常出现了就重新进行发送
     *      失败 重试 时间越来越久 10s 30s 60s 1m 2m 10m
     *
     *    DefaultMessageListenerOrderly.class
     *    https://blog.csdn.net/qq_38154587/article/details/127729803
     *
     * @param message
     */
    @Override
    public void onMessage(MessageExt message) {

        try {
            String orderId = new String(message.getBody(),"utf-8");
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("order_id",orderId);
            log.info("收到订单过期消息， 订单id={}",orderId);
            PayOrder payOrder = payOrderMapper.selectOne(queryWrapper);
            // 订单已支付，不用修改
            if(payOrder.getStatus() == OrderStatusConstant.ORDER_STATUS_IS_PAY){
                log.debug("订单到期，订单已支付，忽略不做操作， 订单={}",payOrder);
                return;
            }
            log.info("订单到期， 订单={}",payOrder);
            payOrder.setStatus(OrderStatusConstant.ORDER_STATUS_IS_EXT);
            payOrderMapper.update(payOrder,queryWrapper);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
