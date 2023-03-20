package com.hyt.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    //配置静态资源的位置
//注意：AppConstant.FILE_MAP="/**"; 表示可以直接访问ip:端口/文件夹名字/图片名字访问
//AppConstant.FILE_MAP="/demo/**"; 表示可以直接访问ip:端口/demo/文件夹名字/图片名字访问
//addResourceLocations("file:" + AppConstant.FILE_PATH); 注意 一定要添加"file:",AppConstant.FILE_PATH是图片存放路径
//
//    @Value("${userImg.url}")
//    private String url;
//    @Value("${userImg.path}")
//    private String path;
//    @Value("${userImg.sfzurl}")
//    private String sfzurl;
//    @Value("${userImg.sfz}")
//    private String sfz;
//
//    @Value("${ht.path}")
//    private String htPath;
//    @Value("${ht.url}")
//    private String htUrl;
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

