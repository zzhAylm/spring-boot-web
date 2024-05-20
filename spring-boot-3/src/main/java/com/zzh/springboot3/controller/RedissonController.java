package com.zzh.springboot3.controller;

import com.zzh.springboot3.service.RedissonService;
import jakarta.annotation.Resource;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/5/20 16:30
 */
@RestController
@RequestMapping("/redis")
public class RedissonController {


    @Resource
    private RedissonService redissonService;

    @GetMapping
    public void get(){
        redissonService.getLock();
    }


    @GetMapping("/lock")
    public void lock(){
        redissonService.lock();
    }
}
