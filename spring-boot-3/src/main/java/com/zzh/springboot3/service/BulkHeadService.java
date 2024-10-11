package com.zzh.springboot3.service;

import com.zzh.springboot3.resilience4j.AbstractBulkheadDecorator;
import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.bulkhead.ThreadPoolBulkhead;
import io.github.resilience4j.decorators.Decorators;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.CompletionStage;
import java.util.function.Supplier;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/9/20 17:30
 */
@Slf4j
@Service
@RefreshScope
public class BulkHeadService {


    public void bulkHead() {
        BulkheadConfig config = BulkheadConfig.custom()
                .maxConcurrentCalls(1) // 最大并发调用数
                .maxWaitDuration(Duration.ofMillis(100)) // 超时时间
                .maxConcurrentCalls(100)
                .build();
        Bulkhead bulkhead = Bulkhead.of("bulkHead", config);
        Decorators.ofRunnable(() -> {
            log.info("bulkhead is :{}", bulkhead.getMetrics().getAvailableConcurrentCalls());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).withBulkhead(bulkhead).decorate().run();
    }


    public void threadPoolBulkHead() {
        ThreadPoolBulkhead bulkhead = ThreadPoolBulkhead.ofDefaults("threadPoolBulkhead");

        // Decorate or execute immediately a lambda expression with a ThreadPoolBulkhead.
        Supplier<CompletionStage<String>> supplier = ThreadPoolBulkhead
                .decorateSupplier(bulkhead, () -> {

                    return "";
                });

        CompletionStage<String> execution = bulkhead
                .executeSupplier(() -> {
                    return "";
                });
    }


    @Resource
    private AbstractBulkheadDecorator bulkheadDecorator;

    public void bulkheadA() {
        bulkheadDecorator.bulkheadMethod();
    }

    @io.github.resilience4j.bulkhead.annotation.Bulkhead(name = "bulkheadAnnotation", type = io.github.resilience4j.bulkhead.annotation.Bulkhead.Type.SEMAPHORE, fallbackMethod = "bulkheadFallbackMethod")
    public void bulkheadB() {
        log.info("annotation bulkhead running.");
    }

    public void bulkheadFallbackMethod(Throwable t) {
        log.info("bulkheadFallbackMethod running,error is:", t);
    }


    public void threadPoolBulkheadA() {
        bulkheadDecorator.threadPoolBulkheadMethod();
    }

    @io.github.resilience4j.bulkhead.annotation.Bulkhead(name = "threadPoolBulkheadAnnotation", type = io.github.resilience4j.bulkhead.annotation.Bulkhead.Type.THREADPOOL, fallbackMethod = "threadPoolBulkheadFallbackMethod")
    public void threadPoolBulkheadB() {
        log.info("annotation threadPoolBulkheadB running.");
    }

    public void threadPoolBulkheadFallbackMethod(Throwable t) {
        log.info("threadPoolBulkheadFallbackMethod running,error is:", t);
    }
}
