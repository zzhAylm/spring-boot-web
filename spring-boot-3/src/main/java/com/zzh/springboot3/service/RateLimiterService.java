package com.zzh.springboot3.service;

import com.zzh.springboot3.resilience4j.AbstractRateLimiterDecorator;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/9/20 17:29
 */
@Slf4j
@Service
public class RateLimiterService {

    @RateLimiter(name = "rateLimiterA", fallbackMethod = "fallbackMethod")
    public void rateLimit() {
        log.info("rateLimit running start.");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("rateLimit running end.");
    }

    public void fallbackMethod(Throwable t) {
        log.info("fallbackMethod is running,error is :", t);
    }

    @Resource
    private AbstractRateLimiterDecorator rateLimiterDecorator;

    public void rateLimiterDecorator() {
        rateLimiterDecorator.rateLimiterMethod();
    }

}
