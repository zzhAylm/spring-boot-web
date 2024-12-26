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
}
