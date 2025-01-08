package com.zzh.springboot.resilience4j.resilience4j;

import com.zzh.springboot.resilience4j.config.Resilience4jProperties;
import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.bulkhead.ThreadPoolBulkhead;
import io.github.resilience4j.bulkhead.ThreadPoolBulkheadConfig;
import io.github.resilience4j.decorators.Decorators;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/10/11 11:53
 */
@Slf4j
public abstract class AbstractBulkheadDecorator {


    BulkheadConfig bulkheadConfig(Resilience4jProperties resilience4jProperties) {
        return BulkheadConfig.custom().maxConcurrentCalls(1).build();
    }

    ThreadPoolBulkheadConfig threadPoolBulkheadConfig(Resilience4jProperties resilience4jProperties) {
        return ThreadPoolBulkheadConfig.custom().coreThreadPoolSize(1).maxThreadPoolSize(1).queueCapacity(0).build();
    }

    abstract Bulkhead getBulkhead();

    abstract ThreadPoolBulkhead getThreadPoolBulkhead();

    abstract void doBulkheadMethod();

    abstract String doThreadPoolBulkheadMethod();

    public void bulkheadMethod() {
        Decorators.ofRunnable(this::doBulkheadMethod).withBulkhead(getBulkhead()).decorate().run();
    }

    public void threadPoolBulkheadMethod() {
        Decorators.ofCallable(this::doThreadPoolBulkheadMethod)
                .withThreadPoolBulkhead(getThreadPoolBulkhead())
                .decorate()
                .get().whenComplete((t, u) -> log.info("response is {},u is {}", t, u));
    }
}
