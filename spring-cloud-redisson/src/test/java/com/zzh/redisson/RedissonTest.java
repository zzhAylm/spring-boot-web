package com.zzh.redisson;

import cn.hutool.json.JSONUtil;
import com.zzh.redisson.service.RedissonService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.RedissonReactive;
import org.redisson.api.RAtomicLongReactive;
import org.redisson.api.RBucket;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.api.annotation.REntity;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/12/16 20:56
 */
@Slf4j
@SpringBootTest
public class RedissonTest {


    @Resource
    private RedissonClient redissonClient;


    @Resource
    private RedissonReactive redissonReactive;


    @Test
    public void redissonTest(){
        RMap<Object, Object> redissonClientMap = redissonClient.getMap("1111");
        redissonClientMap.put("name","zzh");
        log.info("map is :{}", JSONUtil.toJsonStr(redissonClientMap));
    }

    @Resource
    private RedissonService redissonService;


    @Test
    public void redissonService(){
        redissonService.redisMap();
    }

    @Test
    public void redissonRxClientTest(){
        RAtomicLongReactive atomicLong = redissonReactive.getAtomicLong("long_zzh");
        Mono<Boolean> booleanMono = atomicLong.compareAndSet(0, 10);

        atomicLong.get().doOnSuccess((res)->{
            log.info("res is :{}",res );
        }).subscribe();
    }











}


