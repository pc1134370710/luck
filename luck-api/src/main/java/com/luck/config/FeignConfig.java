package com.luck.config;

/**
 * @description:
 * @author: pangcheng
 * @time: 2023/2/2 14:02
 */

import com.luck.constant.Constant;
import com.luck.model.UserAuth;
import com.luck.utils.UserInfoThreadLocal;
import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: pangcheng
 * @create: 2023-02-26 13:06
 **/
@Configuration
@EnableFeignClients(defaultConfiguration = FeignConfig.class) // 开启 fegin 客户端
public class FeignConfig  {

    /**
     * hystrix的默认隔离策略是线程隔离。
     *
     * 在这种情况下，转发调用和真正请求的不是同一个线程，因此没有办法获取到ThreadLocal中的数据。
     *
     * 此时，ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
     * 拿到的attributes是null，因为这里用到了ThreadLocal。
     * 因此在线程隔离策略下，服务端拿不到header信息。那么怎么办呢？
     * 解决方案一：修改默认隔离策略为信号量模式
     * hystrix.command.default.execution.isolation.strategy=SEMAPHORE，这样的话转发线程和请求线程实际上是一个线程。
     * 但是这种方法有些弊端，hystrix官方不建议使用信号量模式。
     *
     * 解决方案二：自定义hystrix隔离策略
     * 思路是将现有的并发策略作为新并发策略的成员变量。在新并发策略中，返回现有并发策略的线程池、Queue，将策略加到Spring容器即可。
     * 目前，Spring Cloud Sleuth以及Spring Security都通过该方式传递 ThreadLocal 对象。
     *
     *
     * @return
     */
    @Bean
    public RequestInterceptor requestInterceptor(){
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                UserAuth userInfo = UserInfoThreadLocal.get();
                //请求头添加添加token
                requestTemplate.header(Constant.ACCESS_TOKEN,  userInfo.getToken());
            }
        };
    }

    /**
     * 增加日志
     * @return
     */
    @Bean
    public Logger.Level feignLogLevel(){
        return Logger.Level.FULL;
    }
//
//    @Bean
//    Logger feignLogger() {
//        return new MyFeignLogger();
//    }


}
