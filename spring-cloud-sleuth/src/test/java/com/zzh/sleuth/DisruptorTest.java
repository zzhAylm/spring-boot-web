package com.zzh.sleuth;

import com.zzh.sleuth.disruptor.Event;
import com.zzh.sleuth.disruptor.StringDisruptorService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.api.RMap;
import org.redisson.api.RStream;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/12/6 15:39
 */
@Slf4j
@SpringBootTest
public class DisruptorTest {

    @Resource
    private StringDisruptorService disruptorService;

    @Test
    public void testStringDisruptorTest() throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            disruptorService.publish("时间：" + System.currentTimeMillis() + "，线程：" + Thread.currentThread() + "，set event");
            Thread.sleep(100);
        }
    }

    @Resource
    private RedissonClient redissonClient;


    @Test
    public void redissonClientTest() {
        RMap<Object, Object> redissonClientMap = redissonClient.getMap("test");
        redissonClientMap.put("key", "value");
        log.info("redissonClientMap get key is :{}", redissonClientMap.get("key"));
    }

    @Test
    public void redissonClientTest_2() {
        RStream<Object, Object> testStream = redissonClient.getStream("testStream");
//        testStream.
    }


}
