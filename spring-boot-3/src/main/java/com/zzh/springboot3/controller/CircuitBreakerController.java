package com.zzh.springboot3.controller;

import com.zzh.springboot3.service.CircuitBreakerService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/9/10 11:34
 */
@Slf4j
@RestController
@RequestMapping("/circuitBreaker")
public class CircuitBreakerController {

    @Resource
    private CircuitBreakerService breakerService;

    @RequestMapping()
    public void circuitBreaker() {
        breakerService.circuitBreaker();
    }

    @RequestMapping("/b")
    public void circuitBreakerB() {
        breakerService.circuitBreakerB();
    }

}
