package com.example.loadbalancertest.controller;

import com.example.loadbalancertest.service.TestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {
    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping(value = "/test")
    public ResponseEntity<?> test(){
        testService.test();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
