package com.zzh.springboot.resilience4j.resilience4j;

import com.zzh.springboot.resilience4j.config.Resilience4jProperties;
import io.github.resilience4j.retry.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/10/11 16:31
 */
@Slf4j
@Component
public class RetryDecorator extends AbstractRetryDecorator {

    private final Retry retry;
    private final static String RETRY_NAME = "retryB";

    public RetryDecorator(Resilience4jProperties resilience4jProperties) {
        this.retry = Resilience4j.getInstance().getRetryRegistryInstance().retry(RETRY_NAME, retryConfig(resilience4jProperties));
    }

    @Override
    void doRetryMethod() {
        log.info("do retry method run start.");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (System.currentTimeMillis() > 100) {
            throw new RuntimeException("retry method 发生异常");
        }
        log.info("do retry method run end.");
    }

    @Override
    Retry getRetry() {
        return this.retry;
    }
}
