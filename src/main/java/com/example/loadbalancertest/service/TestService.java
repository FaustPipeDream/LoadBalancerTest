package com.example.loadbalancertest.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TestService {
    private final RestTemplate restTemplate;

    public TestService(@Qualifier("MyRestTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public void test(){
        restTemplate.getForObject("http://stores/api/test",String.class);
    }
}
