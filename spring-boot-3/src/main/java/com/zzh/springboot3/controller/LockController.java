package com.zzh.springboot3.controller;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.*;
import java.util.concurrent.locks.StampedLock;

/**
 * @Description:
 * @Author: zzh
 * @Create 2025/5/15 17:52
 */

@Slf4j
@RestController
@RequestMapping("/lock")
public class LockController {

    @Resource
    private RedissonClient redissonClient;

    private static final String productId = "UUID";

    @PostMapping("/shop")
    public String shop() throws InterruptedException {
        RLock lock = redissonClient.getLock(productId);
        if (lock.tryLock(1, 10, TimeUnit.SECONDS)) {
            try {
                log.info("获取锁成功");
                //查询库存
                RAtomicLong count = redissonClient.getAtomicLong("count");
                if (count.get() <= 0) {
                    log.info("已经卖完啦");
                    return "已经卖完啦";
                }
                log.info("库存数量：{}", count);
                //减库存
                long num = count.decrementAndGet();
                // 发送MQ，异步创建订单
                Thread.sleep(100);
                log.info("购买成功，剩余数量：{}", num);
                return "购买成功";
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        } else {
            log.info("获取锁失败，系统繁忙，请稍后重试");
            return "系统繁忙，请稍后重试";
        }
    }


    @GetMapping("/user")
    public void userInfo() {


    }

    private Integer value = 10;

    private StampedLock stampedLock = new StampedLock();

    @GetMapping("/stamp")
    public void stampLock() {
        int readV;
        long stamp = stampedLock.tryOptimisticRead();
        readV = value;
        if (!stampedLock.validate(stamp)) {
            stamp = stampedLock.readLock();
            try {
                readV = value;
                log.info("悲观读锁：{}", readV);
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                stampedLock.unlockRead(stamp);
            }
        }
        try {
            log.info("乐观读锁：{}", readV);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private ExecutorService executorService = new ThreadPoolExecutor(2, 3, 100, TimeUnit.SECONDS, new LinkedBlockingQueue<>());


    @Test
    public void addTask() throws InterruptedException {
        executorService.submit(() -> {
            log.info("第一个任务提交,{}",Thread.currentThread().getName());
        });

        Thread.sleep(1000);

        executorService.submit(() -> {
            log.info("第二个任务提交,{}",Thread.currentThread().getName());
        });

    }






}
