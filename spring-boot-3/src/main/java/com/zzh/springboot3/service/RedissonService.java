package com.zzh.springboot3.service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.*;
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
    private static final String BUCKET = "REDIS_DISTRIBUTED_BUCKET";
    private static final String BITMAP = "REDIS_DISTRIBUTED_BITMAP";
    private static final String BLOOM = "REDIS_DISTRIBUTED_BLOOM";

    @Resource(name = "redisson")
    private RedissonClient redisson;

    private static RLock lock;

    private static RBucket<Object> bucket;

    private RBitSet bitSet;

    private RBloomFilter<Object> bloomFilter;

    @PostConstruct
    public void init() {
        lock = redisson.getLock(LOCK);
        bucket = redisson.getBucket(BUCKET);
        this.bloomFilter = redisson.getBloomFilter(BLOOM);
        this.bloomFilter.tryInit(1000000, 0.03);
        this.bitSet = redisson.getBitSet(BITMAP);
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


    // 布隆过滤器的使用
    public void bloomFilter(int num) {
        bloomFilter.add(num);
    }

    public boolean bloomFilterGet(int num) {
       return bloomFilter.contains(num);
    }


    // bitmap的使用
    public void bitmap(int num) {
        bitSet.set(num);
    }

    public boolean bitmapGet(int num) {
        return bitSet.get(num);
    }


}
