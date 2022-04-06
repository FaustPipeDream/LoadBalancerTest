package com.example.loadbalancertest.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultRequestContext;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.RequestData;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.loadbalancer.core.NoopServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.RoundRobinLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
public class CustomLoadBalancer extends RoundRobinLoadBalancer {

    ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierObjectProvider;
    String serviceId;


    public CustomLoadBalancer(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider, String serviceId) {
        super(serviceInstanceListSupplierProvider, serviceId);
        this.serviceInstanceListSupplierObjectProvider = serviceInstanceListSupplierProvider;
        this.serviceId = serviceId;
    }

    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
        ServiceInstanceListSupplier supplier = serviceInstanceListSupplierObjectProvider.getIfAvailable(NoopServiceInstanceListSupplier::new);
        return supplier.get(request).next().map(serviceInstances -> getInstanceResponse(serviceInstances,request));
    }

    Response<ServiceInstance> getInstanceResponse(List<ServiceInstance> serviceInstanceList, Request request){
        DefaultRequestContext requestContext = (DefaultRequestContext) request.getContext();
        //cannot cast to RequestData
        RequestData clientRequest = (RequestData) requestContext.getClientRequest();

        return super.choose(request).block();
    }

}
