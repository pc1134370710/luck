package com.luck.config;

import com.luck.service.IActivityService;
import com.luck.service.impl.ActivityServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @description: 容器启动后
 * @author: pangcheng
 * @create: 2023-02-28 17:36
 **/
@Component
public class ApplicationStartup implements ApplicationRunner {

    private static Logger log  =  LoggerFactory.getLogger(ApplicationStartup.class);

    @Autowired
    private IActivityService activityService;




    /**
     *  springboot项目启动时 完成 走该回调
     * @param args
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("===============缓存预热===================");
        activityService.initActivityStock();
    }
}
