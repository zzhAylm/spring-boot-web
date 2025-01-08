package com.zzh.springboot.resilience4j.resilience4j;

import com.zzh.springboot.resilience4j.config.Resilience4jProperties;
import io.github.resilience4j.decorators.Decorators;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;

import java.time.Duration;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/10/11 16:29
 */
public abstract class AbstractRetryDecorator {

    abstract void doRetryMethod();

    abstract Retry getRetry();

    public void retryMethod() {
        Decorators.ofRunnable(this::doRetryMethod).withRetry(getRetry()).decorate().run();
    }

    RetryConfig retryConfig(Resilience4jProperties resilience4jProperties) {
        return RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofMillis(500))
                .retryExceptions(Throwable.class)
                .build();
    }

}
