package com.zzh.springboot3.service;

import com.zzh.springboot3.resilience4j.AbstractRetryDecorator;
import io.github.resilience4j.decorators.Decorators;
import io.github.resilience4j.retry.Retry;
import jakarta.annotation.Resource;
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


    @Resource
    private AbstractRetryDecorator retryDecorator;

    public void retryA() {
        retryDecorator.retryMethod();
    }


    @io.github.resilience4j.retry.annotation.Retry(name = "retryB", fallbackMethod = "fallbackMethodRetryB")
    public void retryB() {
        log.info("retryB is run start.");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (System.currentTimeMillis() > 100) {
            throw new RuntimeException("retry method 发生异常");
        }
        log.info("retryB is run end.");
    }

    public void fallbackMethodRetryB(Throwable t) {
        log.info("fallbackMethod is running,error is:", t);
    }


}
