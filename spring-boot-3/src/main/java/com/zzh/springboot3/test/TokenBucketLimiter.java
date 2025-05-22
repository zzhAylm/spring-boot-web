package com.zzh.springboot3.test;

import java.util.concurrent.atomic.AtomicInteger;

public class TokenBucketLimiter {
    private final int capacity;             // 桶的容量
    private final int refillRate;           // 每秒生成的令牌数
    private final AtomicInteger tokens;     // 当前令牌数
    private long lastRefillTimestamp;       // 上次填充令牌的时间戳（毫秒）

    public TokenBucketLimiter(int capacity, int refillRate) {
        this.capacity = capacity;
        this.refillRate = refillRate;
        this.tokens = new AtomicInteger(capacity);
        this.lastRefillTimestamp = System.currentTimeMillis();
    }

    // 请求一个令牌
    public synchronized boolean tryAcquire() {
        refill();
        if (tokens.get() > 0) {
            tokens.decrementAndGet();
            return true;
        }
        return false;
    }

    // 根据时间补充令牌
    private void refill() {
        long now = System.currentTimeMillis();
        long elapsed = now - lastRefillTimestamp;
        int tokensToAdd = (int) (elapsed / 1000.0 * refillRate);
        if (tokensToAdd > 0) {
            int newTokens = Math.min(capacity, tokens.get() + tokensToAdd);
            tokens.set(newTokens);
            lastRefillTimestamp = now;
        }
    }





}
