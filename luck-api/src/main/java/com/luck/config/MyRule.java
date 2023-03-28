package com.luck.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @description:
 * @author: pangcheng
 * @create: 2023-03-25 13:29
 **/
//@Configurable
/*
@Configurable
    手动new出来的对象，正常情况下，Spring是无法依赖注入的，这个时候可以使用@Configurable注解；
    bean 原本在Spring 中， 但是是使用时通过new出来的

 */

@Configuration
public class MyRule {


//    @Bean
    public ReactorLoadBalancer<ServiceInstance> getNacosWeightedRule(Environment environment,
                                                                     LoadBalancerClientFactory loadBalancerClientFactory){
        String name = environment.getProperty(LoadBalancerClientFactory.NAMESPACE);
        return new NacosWeightedRule(loadBalancerClientFactory
                .getLazyProvider(name, ServiceInstanceListSupplier.class),name);
    }
}
