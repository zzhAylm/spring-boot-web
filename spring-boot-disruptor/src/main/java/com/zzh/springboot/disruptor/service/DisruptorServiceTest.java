package com.zzh.springboot.disruptor.service;

import com.zzh.springboot.disruptor.disruptor.StringDisruptorService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2025/1/14 15:39
 */
@SpringBootTest
public class DisruptorServiceTest {

    @Resource
    private StringDisruptorService stringDisruptorService;

    @Test
    public void disruptorTest(){
        stringDisruptorService.publish("hello");
    }

}
