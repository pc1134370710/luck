package com.luck;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @description:
 * @author: pangcheng
 * @time: 2023/3/28 16:37
 */
@SpringBootApplication(scanBasePackages = "com.luck.*")
@MapperScan("com.luck.mapper")
public class ActivityApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivityApplication.class,args);
    }
}
