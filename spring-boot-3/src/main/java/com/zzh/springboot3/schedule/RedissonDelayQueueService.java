package com.zzh.springboot3.schedule;

import cn.hutool.json.JSONUtil;
import io.netty.util.concurrent.DefaultThreadFactory;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/10/31 15:23
 */
@Slf4j
@Service
public class RedissonDelayQueueService {

    private static final String BLOCK_DELAY_QUEUE = "blockDelayQueue";

    private final RBlockingQueue<String> blockingQueue;

    private final RDelayedQueue<String> delayedQueue;

    private final ThreadPoolExecutor threadPoolExecutor;

    public RedissonDelayQueueService(RedissonClient redisson) {
        this.blockingQueue = redisson.getBlockingQueue(BLOCK_DELAY_QUEUE);
        this.delayedQueue = redisson.getDelayedQueue(blockingQueue);
        this.threadPoolExecutor = new ThreadPoolExecutor(
                2,
                4,
                100,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1024),
                new CustomThreadFactory());
    }

    @PostConstruct
    public void init() {
        startConsumer();
    }

    private void startConsumer() {
        threadPoolExecutor.submit(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    String take = blockingQueue.take();
                    log.info("监听到延迟任务：{}", take);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                    break;
                }
            }
        });
    }

    public void addTask(Object task, Long delay) {
        if (Objects.isNull(task)) {
            return;
        }
        log.info("添加任务：{},延迟时间：{}", JSONUtil.toJsonStr(task), delay);
        delayedQueue.offer(JSONUtil.toJsonStr(task), delay, TimeUnit.SECONDS);
    }

    public static class CustomThreadFactory implements ThreadFactory {

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r, "delay-thread-pool");
            thread.setDaemon(true);
            return thread;
        }
    }

}
