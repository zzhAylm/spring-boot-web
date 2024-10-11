package com.zzh.springboot3.resilience4j;

import com.zzh.springboot3.config.Resilience4jProperties;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.internal.AtomicRateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/10/11 15:36
 */
@Slf4j
@Component
public class RateLimiterDecorator extends AbstractRateLimiterDecorator {

    private final RateLimiter rateLimiter;

    private final static String RATE_LIMITER_NAME = "rateLimiterB";

    public RateLimiterDecorator(Resilience4jProperties resilience4jProperties) {
        rateLimiter = Resilience4j.getInstance().getRateLimiterRegistryInstance().rateLimiter(RATE_LIMITER_NAME, rateLimiterConfig(resilience4jProperties));
    }

    @Override
    RateLimiter getRateLimiter() {
        return this.rateLimiter;
    }

    @Override
    void doMethod() {
        log.info("rateLimiter run start.");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("rateLimiter run end.");
    }
}
