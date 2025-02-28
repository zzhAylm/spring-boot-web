package com.zzh.springboot3.redisson;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/10/31 21:04
 */
@Slf4j
@SpringBootTest
public class
RedissonServiceTest {

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @Test
    public void redisson() {

    }


}
