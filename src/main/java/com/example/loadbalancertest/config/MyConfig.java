package com.example.loadbalancertest.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@LoadBalancerClients(defaultConfiguration = CustomLoadBalancerConfig.class)
public class MyConfig {
    @LoadBalanced
    @Bean("MyRestTemplate")
    RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
