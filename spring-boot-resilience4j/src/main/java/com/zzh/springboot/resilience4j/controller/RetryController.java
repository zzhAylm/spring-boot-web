package com.zzh.springboot.resilience4j.controller;

import com.zzh.springboot.resilience4j.service.RetryService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/10/11 16:48
 */
@Slf4j
@RestController
@RequestMapping("/retry")
public class RetryController {

    @Resource
    private RetryService retryService;

    @RequestMapping("/a")
    public void retryA() {
        retryService.retryA();
    }

    @RequestMapping("/b")
    public void retryB() {
        retryService.retryB();
    }
}
