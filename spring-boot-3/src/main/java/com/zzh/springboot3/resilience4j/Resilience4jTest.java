package com.zzh.springboot3.resilience4j;

import io.github.resilience4j.cache.Cache;
import io.github.resilience4j.core.functions.CheckedFunction;
import io.github.resilience4j.decorators.Decorators;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import javax.cache.Caching;
import java.util.concurrent.*;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/9/9 17:36
 */
@Slf4j
public class Resilience4jTest {


    // 熔断器
    @Test
    public void resilience4jCircuitBreaker() {


    }
    // 限流器

    // 重试

    // 隔离


    @Test
    public void timeLimiter() throws Exception {
        TimeLimiterRegistry timeLimiterRegistry = TimeLimiterRegistry.ofDefaults();
        TimeLimiter timeLimiter = timeLimiterRegistry.timeLimiter("timeLimiterA");
        // 需要一个调度器对非阻塞CompletableFuture进行调度，控制超时时间
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);
        CompletableFuture<String> result = timeLimiter.executeCompletionStage(
                scheduler, () -> CompletableFuture.supplyAsync(() -> {
                    return "zzh";
                })).toCompletableFuture();
        log.info("result is : {}", result.get(1, TimeUnit.SECONDS));

        Callable<String> callable = timeLimiter.decorateFutureSupplier(() -> CompletableFuture.supplyAsync(() -> {
            return "zzh";
        }));
        log.info("call back is : {}", callable.call());
    }


    @Test
    public void cacheTest() throws Throwable {
        // 创建CacheContext封装为JCache实例
        javax.cache.Cache<String, String> cacheInstance = Caching
                .getCache("cacheName", String.class, String.class);

        Cache<String, String> cacheContext = Cache.of(cacheInstance);


        CheckedFunction<String, String> checkedFunction = Decorators
                .ofCheckedSupplier(() -> "zzh")
                .withCache(cacheContext)
                .decorate();
        String cacheKey = checkedFunction.apply("cacheKey");
        log.info("cache key is :{}",cacheKey);
    }



}
