package com.zzh.springboot3.controller;

import com.zzh.springboot3.service.RateLimiterService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/10/11 15:27
 */
@Slf4j
@RestController
@RequestMapping("/rateLimit")
public class RateLimitController {

    @Resource
    private RateLimiterService rateLimiterService;

    @RequestMapping("/a")
    public void rateLimitA() {
        rateLimiterService.rateLimit();
    }

    @RequestMapping("/b")
    public void rateLimiterB() {
        rateLimiterService.rateLimiterDecorator();
    }
}
