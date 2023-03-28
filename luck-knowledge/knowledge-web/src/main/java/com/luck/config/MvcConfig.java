package com.luck.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    // 注册拦截器

    // 添加拦截器到 spring mvc 拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截所有请求
//        registry.addInterceptor(myInterceptor()).addPathPatterns("/**")
//        .excludePathPatterns();
    }

    /**
     * 资源映射
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

//        registry.addResourceHandler(url+"**")
//                .addResourceLocations("file:"+path);
//        registry.addResourceHandler(sfzurl+"**")
//                .addResourceLocations("file:"+sfz);
//        //让 虚拟 路径  跟  真实路径 建立 映射关系
//        registry.addResourceHandler(htUrl+"**")
//                .addResourceLocations("file:"+htPath);
    }
}
