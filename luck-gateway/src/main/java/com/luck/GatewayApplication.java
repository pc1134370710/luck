package com.luck;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient // 开启 nacos 客户端
@EnableFeignClients // 开启 Feign 功能
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class,args);
    }

//    @Bean
//    public CorsWebFilter corsWebFilter(){
//        org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource source
//                = new UrlBasedCorsConfigurationSource(new PathPatternParser());
//
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        // 预检请求的缓存时间（秒），即在这个时间段里，对于相同的跨域请求不会再预检了
//        corsConfiguration.setMaxAge(18000L);
//        // 允许cookies跨域
//        corsConfiguration.setAllowCredentials(true);
//        // 允许向该服务器提交请求的URI，*表示全部允许，在SpringMVC中，如果设成*，会自动转成当前请求头中的Origin
//        corsConfiguration.addAllowedOrigin("*");
//        // 允许访问的头信息,*表示全部
//        corsConfiguration.addAllowedHeader("*");
//        // 允许提交请求的方法类型，*表示全部允许
//        corsConfiguration.addAllowedMethod("*");
//        corsConfiguration.addAllowedOriginPattern("*");
//
//        //允许跨域的路径
//        source.registerCorsConfiguration("/**",corsConfiguration);
//        return new CorsWebFilter(source);
//    }

    /*
        registry.addMapping("/**").
                allowedOrigins("*"). //允许跨域的域名，可以用*表示允许任何域名使用
                allowedMethods("*"). //允许任何方法（post、get等）
                allowedHeaders("*"). //允许任何请求头
                allowCredentials(true);//带上cookie信息
        // 设置允许跨域的路由
        registry.addMapping("/**")
                // 设置允许跨域请求的域名------------修改此行
                .allowedOriginPatterns("*")
                // 是否允许证书（cookies）
                .allowCredentials(true)
                // 设置允许的方法
                .allowedMethods("*")
                // 跨域允许时间
                .maxAge(3600);

     */
}
