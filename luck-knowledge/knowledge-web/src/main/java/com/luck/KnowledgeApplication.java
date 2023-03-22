package com.luck;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.luck.*")
@MapperScan("com.luck.mapper")
public class KnowledgeApplication {

	public static void main(String[] args) {
		SpringApplication.run(KnowledgeApplication.class, args);
	}



}
