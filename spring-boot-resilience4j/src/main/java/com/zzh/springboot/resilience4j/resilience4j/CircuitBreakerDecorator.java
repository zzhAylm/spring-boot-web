package com.zzh.springboot.resilience4j.resilience4j;

import com.zzh.springboot.resilience4j.config.Resilience4jProperties;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/9/10 15:39
 */
@Slf4j
@Component
public class CircuitBreakerDecorator extends AbstractCircuitBreakerDecorator {

    public final String CIRCUIT_BREAKER_B = "circuitBreakerB";

    private final CircuitBreaker circuitBreaker;

    public CircuitBreakerDecorator(Resilience4jProperties resilience4jProperties) {
        this.circuitBreaker = Resilience4j.getInstance().getCircuitBreakerInstance().circuitBreaker(CIRCUIT_BREAKER_B, buildConfig(resilience4jProperties.getCircuitBreaker().get(CIRCUIT_BREAKER_B)));
    }

    @Override
    public CircuitBreaker getCircuitBreaker() {
        return this.circuitBreaker;
    }

    @Override
    public void doMethod() {
        try {
            log.info("runnable is running!!");
            try {
                Thread.sleep(1000 * 3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            throw new RuntimeException("runnable error!");
        } catch (Exception e) {
            log.error("fail back is running");
        }
    }

}
