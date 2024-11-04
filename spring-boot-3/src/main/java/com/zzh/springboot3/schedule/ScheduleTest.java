package com.zzh.springboot3.schedule;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.Redisson;
import org.redisson.RedissonQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.redisson.command.CommandAsyncExecutor;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 延迟任务任务的执行
 * @Author: zzh
 * @Crete 2024/10/31 13:50
 */
@Slf4j
@SpringBootTest
public class ScheduleTest {

    //定时任务线程池：实现的定时任务
    @Test
    public void scheduleThreadPoolExecutor() throws InterruptedException {
        ScheduledThreadPoolExecutor threadPoolExecutor = new ScheduledThreadPoolExecutor(1);

        threadPoolExecutor.schedule(() -> {
            log.info("没秒执行一次的定时任务");
        }, 1, TimeUnit.SECONDS);

        // 第一次执行时间+固定延迟时间=下次执行
        threadPoolExecutor.scheduleAtFixedRate(() -> {
            log.info("延迟执行的，定时任务");
        }, 1, 1, TimeUnit.SECONDS);

        // 第一次任务完成时间+固定延迟时间=下次执行
        threadPoolExecutor.scheduleWithFixedDelay(() -> {
            log.info("延迟执行的定时任务，任务执行结束后计时");
        }, 1, 1, TimeUnit.SECONDS);

        Thread.sleep(50000);
    }


    @Resource(name = "redisson")
    private RedissonClient redisson;

    // 分布式的延迟任务：redisson 延迟队列实现的任务延迟
    @Test
    public void redisson() {
        RDelayedQueue<Object> delayQueue = redisson.getDelayedQueue(redisson.getBlockingQueue("delayQueue"));

        JSONObject jsonObject = new JSONObject();
        jsonObject.set("name", "zzh");
        jsonObject.set("age", 18);
        delayQueue.offer(jsonObject,10,TimeUnit.SECONDS);
        Object poll = delayQueue.poll();

        log.info("延迟任务 is ：{}", JSONUtil.toJsonStr(poll));
    }


}
