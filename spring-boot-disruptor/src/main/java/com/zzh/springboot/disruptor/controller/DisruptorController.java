package com.zzh.springboot.disruptor.controller;

import com.zzh.springboot.disruptor.disruptor.StringDisruptorService;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2025/1/14 16:11
 */
@RestController
@RequestMapping("disruptor")
public class DisruptorController {

    @Resource
    private StringDisruptorService disruptorService;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    public static final String KEY_HASH = "disruptor_hash";

    @GetMapping
    public void send() {
        String key = String.valueOf(System.currentTimeMillis());
        redisTemplate.opsForHash().put(KEY_HASH, key, "{\"name\":\"zzh\"}");
        disruptorService.publish(key);
    }

}
