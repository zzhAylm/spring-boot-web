package com.zzh.springboot3.algorithm;

import com.zzh.springboot3.service.RedissonService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class ApplicationTest {

    @Resource
    private RedissonService redissonService;

    @Test
    void test() {
        long startTime = System.currentTimeMillis();
        log.info("application test start is {}", startTime);

        redissonService.getLock();


        long endTime = System.currentTimeMillis();
        log.info("application test end time is {},execution time: {} milliseconds", endTime, endTime - startTime);
    }
}
