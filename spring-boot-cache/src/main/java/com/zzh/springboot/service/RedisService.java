package com.zzh.springboot.service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.*;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: zzh
 * @Create 2025/2/27 13:41
 */
@Slf4j
@Service
public class RedisService {

    @Resource
    private RedissonClient redissonClient;


    private static final String CHANNEL = "channel";
    private static final String QUEUE = "queue";
    private static final String BLOCK_QUEUE = "block_queue";

    private static final String DELAY_BLOCK_QUEUE = "delay_block_queue";

    private static final ExecutorService executorService = Executors.newFixedThreadPool(4);

    @PostConstruct
    public void init() {
        subscribe();
        subscribe();
        subscribe();
//        executorService.submit(this::subscribeQueue);
        executorService.submit(this::subscribeQueue);
        executorService.submit(this::blockQueueSub);
        executorService.submit(this::delayQueueSub);
    }

    // redisson 订阅发布,一对多

    public void publish(String message) {
        redissonClient.getTopic(CHANNEL).publish(message);
    }

    public void subscribe() {
        redissonClient.getTopic(CHANNEL).addListener(String.class, (channel1, msg) -> log.info("channel is :{},message is :{}", channel1.toString(), msg));
    }


    public void sendQueue(String msg) {
        redissonClient.getQueue(QUEUE).add(msg);
    }

    public void subscribeQueue() {
        RQueue<String> queue = redissonClient.getQueue(QUEUE);
        while (true) {
            log.info("consumer queue msg is :{}", queue.poll());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void blockQueueSendMsg(String msg) {
        redissonClient.getBlockingQueue(BLOCK_QUEUE).add(msg);
    }


    public void blockQueueSub() {
        RBlockingQueue<String> blockingQueue = redissonClient.getBlockingQueue(BLOCK_QUEUE);
        while (true) {
            try {
                String take = blockingQueue.take();
                log.info("block queue consumer ,msg is :{}", take);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void delayQueue() {
        log.info("send delay message time is :{}", System.currentTimeMillis());
        RDelayedQueue<String> delayQueue = redissonClient.getDelayedQueue(redissonClient.getBlockingQueue(DELAY_BLOCK_QUEUE));
        delayQueue.offer("delay message send.", 10000, TimeUnit.MILLISECONDS);

    }

    public void delayQueueSub() {
        RBlockingQueue<String> blockingQueue = redissonClient.getBlockingQueue(DELAY_BLOCK_QUEUE);
        while (true) {
            try {
                String take = blockingQueue.take();
                log.info("delay block queue ,consumer  message is :{},time is :{}", take, System.currentTimeMillis());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void lock() {
        RLock lock = redissonClient.getLock("lock");
        if (lock.tryLock()) {
            log.info("获取锁");
            lock.unlock();
        }
    }




}
