package com.zzh.springboot.resilience4j.resilience4j;

import com.zzh.springboot.resilience4j.config.Resilience4jProperties;
import io.github.resilience4j.decorators.Decorators;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;

import java.time.Duration;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/10/11 15:33
 */
public abstract class AbstractRateLimiterDecorator {

    abstract RateLimiter getRateLimiter();

    abstract void doMethod();

    public void rateLimiterMethod() {
        Decorators.ofRunnable(this::doMethod).withRateLimiter(getRateLimiter()).decorate().run();
    }

    RateLimiterConfig rateLimiterConfig(Resilience4jProperties resilience4jProperties) {
        return RateLimiterConfig.custom()
                .limitForPeriod(1)
                .limitRefreshPeriod(Duration.ofSeconds(1))
                .timeoutDuration(Duration.ofSeconds(1))
                .build();
    }
}
