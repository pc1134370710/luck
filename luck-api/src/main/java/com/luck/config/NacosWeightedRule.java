package com.luck.config;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.EmptyResponse;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.loadbalancer.core.*;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @description: 支持Nacos权重配置的负载均衡策略
 * @author: pangcheng
 * @create: 2023-02-25 13:09
 **/
//@Component
public class NacosWeightedRule implements ReactorServiceInstanceLoadBalancer {

    @Autowired
    private NacosDiscoveryProperties nacosDiscoveryProperties;

    private static final Log log = LogFactory.getLog(RandomLoadBalancer.class);
    private  String serviceId;
    private ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;

    public NacosWeightedRule() {
    }

    public NacosWeightedRule(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider, String serviceId) {
        this.serviceId = serviceId;
        this.serviceInstanceListSupplierProvider = serviceInstanceListSupplierProvider;
    }

    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {

        // 获取Nocas服务发现的相关组件API
        NamingService namingService = nacosDiscoveryProperties.namingServiceInstance();

        ServiceInstanceListSupplier supplier = (ServiceInstanceListSupplier)this.serviceInstanceListSupplierProvider.getIfAvailable(NoopServiceInstanceListSupplier::new);

        return supplier.get(request).next().map((serviceInstances) -> {
            return this.processInstanceResponse(supplier, serviceInstances);
        });
    }

    private Response<ServiceInstance> processInstanceResponse(ServiceInstanceListSupplier supplier, List<ServiceInstance> serviceInstances) {
        Response<ServiceInstance> serviceInstanceResponse = this.getInstanceResponse(serviceInstances);
        if (supplier instanceof SelectedInstanceCallback && serviceInstanceResponse.hasServer()) {
            ((SelectedInstanceCallback)supplier).selectedServiceInstance((ServiceInstance)serviceInstanceResponse.getServer());
        }

        return serviceInstanceResponse;
    }

    /**
     * 根据 权重获取对应的 nacos 地址
     * @param instances
     * @return
     */
    private Response<ServiceInstance> getInstanceResponse(List<ServiceInstance> instances) {
        if (instances.isEmpty()) {
            if (log.isWarnEnabled()) {
                log.warn("No servers available for service: " + this.serviceId);
            }

            return new EmptyResponse();
        } else {
            int index = ThreadLocalRandom.current().nextInt(instances.size());



            return new EmptyResponse();
        }
    }
}
