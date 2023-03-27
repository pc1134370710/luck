package com.luck.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


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

