package com.zzh.springboot.resilience4j.resilience4j;

import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadRegistry;
import io.github.resilience4j.bulkhead.ThreadPoolBulkhead;
import io.github.resilience4j.bulkhead.ThreadPoolBulkheadRegistry;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/9/10 14:59
 */
@Slf4j
public class Resilience4j {

    private final CircuitBreakerRegistry circuitBreakerRegister;

    private final BulkheadRegistry bulkheadRegister;

    private final ThreadPoolBulkheadRegistry threadPoolBulkheadRegistry;

    private final RateLimiterRegistry rateLimiterRegistry;

    private final RetryRegistry retryRegistry;

    //熔断
    public CircuitBreakerRegistry getCircuitBreakerInstance() {
        return this.circuitBreakerRegister;
    }

    //信号量隔离
    public BulkheadRegistry getBulkheadRegisterInstance() {
        return this.bulkheadRegister;
    }

    //线程池隔离
    public ThreadPoolBulkheadRegistry getThreadPoolBulkheadRegisterInstance() {
        return this.threadPoolBulkheadRegistry;
    }

    //限流器
    public RateLimiterRegistry getRateLimiterRegistryInstance() {
        return this.rateLimiterRegistry;
    }

    //重试
    public RetryRegistry getRetryRegistryInstance() {
        return this.retryRegistry;
    }

    public static Resilience4j getInstance() {
        return Resilience4jHolder.instance;
    }

    private Resilience4j(CircuitBreakerRegistry circuitBreakerRegistry, BulkheadRegistry bulkheadRegister, ThreadPoolBulkheadRegistry threadPoolBulkheadRegistry, RateLimiterRegistry rateLimiterRegistry, RetryRegistry retryRegistry) {
        this.circuitBreakerRegister = circuitBreakerRegistry;
        this.bulkheadRegister = bulkheadRegister;
        this.threadPoolBulkheadRegistry = threadPoolBulkheadRegistry;
        this.rateLimiterRegistry = rateLimiterRegistry;
        this.retryRegistry = retryRegistry;

        this.circuitBreakerRegister.getEventPublisher()
                .onEntryAdded(entryAddedEvent -> {
                    CircuitBreaker addedCircuitBreaker = entryAddedEvent.getAddedEntry();
                    log.info("CircuitBreaker {} added", addedCircuitBreaker.getName());
                })
                .onEntryRemoved(entryRemovedEvent -> {
                    CircuitBreaker removedCircuitBreaker = entryRemovedEvent.getRemovedEntry();
                    log.info("CircuitBreaker {} removed", removedCircuitBreaker.getName());
                });

        this.bulkheadRegister.getEventPublisher()
                .onEntryAdded(entryAddedEvent -> {
                    Bulkhead addedBulkhead = entryAddedEvent.getAddedEntry();
                    log.info("Bulkhead {} added", addedBulkhead.getName());
                })
                .onEntryRemoved(entryRemovedEvent -> {
                    Bulkhead removedBulkhead = entryRemovedEvent.getRemovedEntry();
                    log.info("Bulkhead {} removed", removedBulkhead.getName());
                });

        this.threadPoolBulkheadRegistry.getEventPublisher()
                .onEntryAdded(entryAddedEvent -> {
                    ThreadPoolBulkhead addedEntry = entryAddedEvent.getAddedEntry();
                    log.info("ThreadPoolBulkhead {} added", addedEntry.getName());
                })
                .onEntryRemoved(entryRemovedEvent -> {
                    ThreadPoolBulkhead removedEntry = entryRemovedEvent.getRemovedEntry();
                    log.info("ThreadPoolBulkhead {} removed", removedEntry.getName());
                });
    }

    private static class Resilience4jHolder {
        private static final CircuitBreakerRegistry circuitBreakerRegister = CircuitBreakerRegistry.ofDefaults();
        private static final BulkheadRegistry bulkheadRegister = BulkheadRegistry.ofDefaults();
        private static final ThreadPoolBulkheadRegistry threadPoolBulkheadRegistry = ThreadPoolBulkheadRegistry.ofDefaults();
        private static final RateLimiterRegistry rateLimiterRegistry = RateLimiterRegistry.ofDefaults();
        private static final RetryRegistry retryRegistry = RetryRegistry.ofDefaults();
        private static final Resilience4j instance = new Resilience4j(circuitBreakerRegister, bulkheadRegister, threadPoolBulkheadRegistry, rateLimiterRegistry, retryRegistry);
    }


}
