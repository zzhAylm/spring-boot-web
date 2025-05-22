package com.zzh.springboot3.test.limit;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.core.config.Scheduled;
import org.apache.logging.log4j.core.util.UuidUtil;
import org.junit.jupiter.api.Test;
import org.redisson.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.Collection;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 限流器
 * @Author: zzh
 * @Create 2025/5/22 10:35
 */
@Slf4j
@SpringBootTest
public class RataLimiterTest {

    @Resource
    private RedissonClient redissonClient;

    private static final int MAX_REQUESTS = 10;

    private static final int EXPIRATION_TIME = 60;

    private static final String REDIS_KEY = "count_rata_limiter";

    public boolean countRateLimiter() {
        RAtomicLong atomicLong = redissonClient.getAtomicLong(REDIS_KEY);
        long count = atomicLong.incrementAndGet();
        if (count == 1) {
            atomicLong.expire(Duration.ofSeconds(EXPIRATION_TIME));
        }
        return count <= MAX_REQUESTS;
    }

    @Test
    public void countRateLimiterTest() {
        for (int i = 0; i < 20; i++) {
            if (countRateLimiter()) {
                log.info("请求成功");
            } else {
                log.info("被限流了");
            }
        }
    }


    private static final int MAX_REQUESTS_WINDOW = 10;

    private static final int EXPIRATION_TIME_WINDOW = 60;

    private static final String REDIS_KEY_WINDOW = "time_window_rata_limiter";

    /***
     * 滑动窗口的限流算法
     * */
    public boolean windowRateLimiter() {
        long currentTime = System.currentTimeMillis() / 1000;
        RScoredSortedSet<String> scoredSortedSet = redissonClient.getScoredSortedSet(REDIS_KEY_WINDOW);
        scoredSortedSet.add(currentTime, currentTime + IdUtil.fastUUID());
        long start = currentTime - EXPIRATION_TIME_WINDOW;
        int removeCount = scoredSortedSet.removeRangeByScore(0, true, start, false);
        log.info("删除窗口数据个数={}", removeCount);
        Collection<String> strings = scoredSortedSet.valueRange(start, true, currentTime, true);
        log.info("窗口 start={},end={},count={}", start, currentTime, strings.size());
        return strings.size() <= MAX_REQUESTS_WINDOW;
    }

    @Test
    public void windowRateLimiterTest() {
        for (int i = 0; i < 20; i++) {
            if (windowRateLimiter()) {
                log.info("请求成功");
            } else {
                log.info("被限流了");
            }
        }
    }

    private static final int MAX_REQUESTS_TOKEN_BUCKET = 10;

    private static final int MAX_SECOND_REQUESTS_TOKEN = 10;

    private static final String REQUESTS_TOKEN_BUCKET_KEY = "token_bucket_rate_limiter";

    public boolean tokenBucketRateLimiter() {

        RList<Object> tokenBucket = redissonClient.getList(REQUESTS_TOKEN_BUCKET_KEY);
        if (tokenBucket.isEmpty()) {
            return false;
        }
        tokenBucket.remove(0);
        return true;

//        RRateLimiter rateLimiter = redissonClient.getRateLimiter("myRateLimiter");
//// 初始化令牌桶：最大容量为10，速率为每秒生成10个令牌
//        rateLimiter.trySetRate(
//                RateType.OVERALL, // OVERALL: 所有客户端共享限流；PER_CLIENT: 单客户端限流
//                10,               // 每秒最多生成的令牌数
//                1,                // 1 秒
//                RateIntervalUnit.SECONDS
//        );


    }

    private static final ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);


    @PostConstruct
    public void init() {
        scheduledThreadPoolExecutor.scheduleAtFixedRate(this::fillTokenBucketRateLimiter, 0, 1, TimeUnit.SECONDS);
    }
    //每秒执行

    public void fillTokenBucketRateLimiter() {
        RList<Object> tokenBucket = redissonClient.getList(REQUESTS_TOKEN_BUCKET_KEY);
        if (tokenBucket.size() >= MAX_REQUESTS_TOKEN_BUCKET) {
            return;
        }
        for (int i = 0; i < MAX_SECOND_REQUESTS_TOKEN; i++) {
            if (tokenBucket.size() >= MAX_REQUESTS_TOKEN_BUCKET) {
                return;
            }
            tokenBucket.add(System.currentTimeMillis());
        }
    }

    @Test
    public void tokenBucketRateLimiterTest() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            if (tokenBucketRateLimiter()) {
                log.info("请求成功");
            } else {
                log.info("被限流了");
            }
        }

        Thread.sleep(1000);
        for (int i = 0; i < 20; i++) {
            if (tokenBucketRateLimiter()) {
                log.info("请求成功");
            } else {
                log.info("被限流了");
            }
        }
    }

}
