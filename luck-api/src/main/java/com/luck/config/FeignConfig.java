package com.luck.config;

/**
 * @description:
 * @author: pangcheng
 * @time: 2023/3/28 14:02
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

/**
 * @description:
 * @author: pangcheng
 * @create: 2023-03-26 13:06
 **/
@Configurable
@EnableFeignClients // 开启 fegin 客户端
public class FeignConfig  {

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
