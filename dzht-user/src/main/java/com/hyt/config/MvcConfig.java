package com.hyt.config;

import com.hyt.dzht.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    // 注册拦截器
    @Bean
    public LoginInterceptor myInterceptor(){
        return new LoginInterceptor();
    }
    // 添加拦截器到 spring mvc 拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截所有请求
        registry.addInterceptor(myInterceptor()).addPathPatterns("/**")
        .excludePathPatterns();
    }



}
