package com.zzh.springboot3.service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/5/20 16:38
 */
@Slf4j
@Service
public class RedissonService {

    private static final String LOCK = "REDIS_DISTRIBUTED_LOCK";

    @Resource(name = "redisson")
    private RedissonClient redisson;

    private static RLock lock;

    @PostConstruct
    public void init() {
        lock = redisson.getLock(LOCK);
    }

    public void getRedisLock() {

        while (true) {
            try {
                if (lock.tryLock(1000 * 10, TimeUnit.MICROSECONDS)) break;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("Thread ID:" + Thread.currentThread().getId() + ",重新获取");
        }
        try {
            log.info("Thread ID:" + Thread.currentThread().getId() + ",获取到锁");
            Thread.sleep(100);
            log.info("Thread ID:" + Thread.currentThread().getId() + ",释放所");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }

    }

    public void getLock() {
        for (int i = 0; i < 10; i++) {
            new Thread(this::getRedisLock).start();
        }
    }


    public void lock() {
        for (int i = 0; i < 10; i++) {
            new Thread(this::redissonLock).start();
        }
    }


    public void redissonLock() {
        try {
            lock.lock();
            log.info("Thread ID:" + Thread.currentThread().getId() + ",获取到锁");
            Thread.sleep(1000);
            log.info("Thread ID:" + Thread.currentThread().getId() + ",释放所");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }

    }
}
