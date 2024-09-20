package com.zzh.springboot3.service;

import io.github.resilience4j.decorators.Decorators;
import io.github.resilience4j.retry.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/9/20 17:31
 */
@Slf4j
@Service
public class RetryService {

    public void retry() {
        Retry retry = Retry.ofDefaults("retryService");
        Decorators.ofRunnable(() -> {
            log.info("retry is :{}", retry);
            throw new RuntimeException("retry 发生异常");
        }).withRetry(retry).decorate().run();

    }


    @io.github.resilience4j.retry.annotation.Retry(name = "retry")
    public void retrySpring() {
        log.info("retry is :{}", System.currentTimeMillis());
        throw new RuntimeException("retry 发生异常");
    }

}
