package com.zzh.springboot3.resilience4j;

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/9/10 14:59
 */
public class Resilience4j {

    private final CircuitBreakerRegistry circuitBreakerRegister;

    public CircuitBreakerRegistry getCircuitBreakerInstance() {
        return this.circuitBreakerRegister;
    }

    public static Resilience4j getInstance() {
        return Resilience4jHolder.instance;
    }

    private Resilience4j(CircuitBreakerRegistry circuitBreakerRegistry) {
        this.circuitBreakerRegister = circuitBreakerRegistry;
    }

    private static class Resilience4jHolder {
        private static final CircuitBreakerRegistry circuitBreakerRegister = CircuitBreakerRegistry.ofDefaults();
        private static final Resilience4j instance = new Resilience4j(circuitBreakerRegister);
    }


}
