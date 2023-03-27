package com.luck.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

/**
 * @description:
 * @author: pangcheng
 * @create: 2023-03-25 13:29
 **/
@Configurable
public class MyRule {


    @Bean
    public ReactorLoadBalancer<ServiceInstance> getNacosWeightedRule(Environment environment,
                                                                     LoadBalancerClientFactory loadBalancerClientFactory){
        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        return new NacosWeightedRule(loadBalancerClientFactory
                .getLazyProvider(name, ServiceInstanceListSupplier.class),name);
    }
}
