package com.luck.config.mq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: pangcheng
 * @time: 2023/3/22 9:37
 */
@Configuration
public class RocketMQConfig {
//
//    @Value("${rocketmq.name-server}")
//    private String rocketMqUrl;
//    @Value("${rocketmq.producer.group}")
//    private String rocketGroupId;
//
//    @Bean
//    public RocketMQTemplate rocketMQTemplate (){
//        // 创建一个 生产者，
//        /*
//        RocketMQ中的消息生产者都是以生产者组（Producer Group）的形式出现的。生产者组是同一类生产
//                者的集合，这类Producer发送相同Topic类型的消息。一个生产者组可以同时发送多个主题的消息。
//         */
//
//        DefaultMQProducer producer = new DefaultMQProducer(rocketGroupId);
//        producer.setNamesrvAddr(rocketMqUrl);
//        System.out.println(" 初始化 MQ url："+rocketMqUrl);
//        // 队列大小为1 ，默认为4
//        producer.setDefaultTopicQueueNums(1);
//        RocketMQTemplate rocketMQTemplate = new RocketMQTemplate();
//
//        // 同步模式下 设置当发送失败时重试次数，默认2，可能会导致消息重复
//        producer.setRetryTimesWhenSendFailed(3);
//        // 设置发送超时时间5s, 默认3s
//        producer.setSendMsgTimeout(5000);
//        rocketMQTemplate.setProducer(producer);
////        rocketMQTemplate.setDefaultDestination();
//        return rocketMQTemplate;
//    }

}
