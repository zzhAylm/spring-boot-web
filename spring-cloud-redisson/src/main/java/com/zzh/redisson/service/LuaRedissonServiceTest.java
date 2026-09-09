package com.zzh.redisson.service;

import cn.hutool.core.io.resource.ResourceUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.RedissonScript;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/12/24 16:01
 */
@Slf4j
@Service
@SpringBootTest
public class LuaRedissonServiceTest {

    @Resource
    private RedissonClient redissonClient;


    @Test
    public void luaRedisClientTest() {

        RScript script = redissonClient.getScript();
        String lueScript = """
                if redis.call("EXISTS", KEYS[1]) == 1 then
                    return redis.call("INCR", KEYS[1])
                else
                    redis.call("SET", KEYS[1], 1)
                    return 1
                end
                """;

        Object eval = script.eval(RScript.Mode.READ_WRITE, lueScript, RScript.ReturnType.INTEGER, List.of("zzh2"));

        log.info("exec lua value is :{}", eval);
    }


    @Test
    public void luaRedisClientTest_1() {
        RScript script = redissonClient.getScript();
        String lueScript = ResourceUtil.readUtf8Str("lua/test.lua");
        Object eval = script.eval(RScript.Mode.READ_WRITE, lueScript, RScript.ReturnType.INTEGER, List.of("zzh3"));
        log.info("exec lua value is :{}", eval);
    }

    @Test
    public void luaRedisClientTest_2() {
        String luaScript = ResourceUtil.readUtf8Str("lua/test_2.lua");
        RScript script = redissonClient.getScript();
        Object eval = script.eval(RScript.Mode.READ_WRITE, luaScript, RScript.ReturnType.STATUS, List.of("ssssssss"), 10, 1);
        log.info("exec lua value is :{}", eval);

    }


    @Test
    public void luaRedisClientTest_3() {
        String luaScript = ResourceUtil.readUtf8Str("lua/test_3.lua");
        RScript script = redissonClient.getScript();
        Object eval = script.eval(RScript.Mode.READ_WRITE, luaScript, RScript.ReturnType.STATUS, List.of("ssssssss"),10,20);
        log.info("exec lua value is :{}", eval);

    }

    @Test
    public void luaComputerparam2() {
        // 定义Lua脚本
        String luaScript = "return  tonumber(ARGV[1]) + tonumber(ARGV[2])"; //这块是需要配置序列化的，不配置总是为nil
        // 执行Lua脚本并获取结果
        RScript script = redissonClient.getScript();
        Long result = script.eval(RScript.Mode.READ_ONLY, luaScript, RScript.ReturnType.INTEGER,
                Collections.singletonList("a"), 1,3);
        System.out.println("计算结果：" + result);

    }

    @Test
    public void testDeductStock() throws InterruptedException {
        String stockKey = "test:stock";
        // 初始化库存100
        redissonClient.getBucket(stockKey).set(100);
        
        // 创建线程池模拟并发
        int threadCount = 10;
        CountDownLatch latch = new CountDownLatch(threadCount);
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        
        // 加载Lua脚本
        String luaScript = ResourceUtil.readUtf8Str("lua/deduct_stock.lua");
        RScript script = redissonClient.getScript();
        
        // 模拟10个线程同时扣减库存
        for (int i = 0; i < threadCount; i++) {
            executorService.execute(() -> {
                try {
                    // 每次扣减5个库存
                    Object result = script.eval(RScript.Mode.READ_WRITE, 
                            luaScript, 
                            RScript.ReturnType.INTEGER, 
                            Collections.singletonList(stockKey), 
                            5);
                    
                    log.info("扣减库存结果: {}", result);
                } finally {
                    latch.countDown();
                }
            });
        }
        
        // 等待所有线程执行完成
        latch.await();
        executorService.shutdown();
        
        // 查看最终库存
        Integer finalStock = (Integer) redissonClient.getBucket(stockKey).get();
        log.info("最终库存: {}", finalStock);
    }

    @Test
    public void testInitStock() {
        String stockKey = "test:stock";
        // 初始化库存
        redissonClient.getBucket(stockKey).set(100);
        log.info("库存初始化完成，当前库存：{}", redissonClient.getBucket(stockKey).get());
    }

//    @Test
//    public void redisCluster(){
//        redissonClient.
//    }



}
