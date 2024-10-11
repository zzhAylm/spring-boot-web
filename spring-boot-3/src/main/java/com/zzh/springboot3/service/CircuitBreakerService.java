package com.zzh.springboot3.service;

import com.zzh.springboot3.resilience4j.AbstractCircuitBreakerDecorator;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/9/10 11:40
 */
@Slf4j
@Service
public class CircuitBreakerService {

    private static final String CIRCUIT_BREAKER_A = "circuitBreakerA";

    @CircuitBreaker(name = CIRCUIT_BREAKER_A, fallbackMethod = "circuitBreakerFallBack")
    public void circuitBreaker() {
        long start = System.currentTimeMillis();
        log.info("circuitBreaker start,time is :{}", start);
        if (start > 100) {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "This is a remote exception");
        }
        log.info("circuitBreaker end,use time :{}", System.currentTimeMillis() - start);
    }

    public void circuitBreakerFallBack(Throwable t) {
        long start = System.currentTimeMillis();
        log.error("circuitBreakerFallBack start,time is :{}", start);
        log.info("circuitBreakerFallBack end,use time :{}", System.currentTimeMillis() - start);
    }

    @Resource
    private AbstractCircuitBreakerDecorator circuitBreakerDecorator;


    public void circuitBreakerB() {
        circuitBreakerDecorator.circuitBreakerMethod();
    }


}
