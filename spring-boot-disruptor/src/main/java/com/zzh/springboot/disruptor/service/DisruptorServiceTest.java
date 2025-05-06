package com.zzh.springboot.disruptor.service;

import com.zzh.springboot.disruptor.disruptor.StringDisruptorService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Set;

import static com.zzh.springboot.disruptor.controller.DisruptorController.KEY_HASH;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2025/1/14 15:39
 */
@Slf4j
@SpringBootTest
public class DisruptorServiceTest {

    @Resource
    private StringDisruptorService stringDisruptorService;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void disruptorTest(){
        stringDisruptorService.publish("hello");

    }


    @Test
    public void redis(){
        String userId = "123";
        String key1 = "user:{" + userId + "}:name";
        String key2 = "user:{" + userId + "}:email";
        redisTemplate.opsForValue().set(key1, "Alice");
        redisTemplate.opsForValue().set(key2, "alice@example.com");
    }


    @Test
    public void redisHash(){
        List<Object> values = redisTemplate.opsForHash().values(KEY_HASH);
        log.info("{}", values);
        Set<Object> keys = redisTemplate.opsForHash().keys(KEY_HASH);
        log.info("{}", keys);
    }

}
