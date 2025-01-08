package com.zzh.springboot.resilience4j.resilience4j;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.common.circuitbreaker.configuration.CommonCircuitBreakerConfigurationProperties;
import io.github.resilience4j.core.ClassUtils;
import io.github.resilience4j.decorators.Decorators;
import io.github.resilience4j.ratelimiter.internal.AtomicRateLimiter;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Predicate;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/9/10 15:42
 */
@Slf4j
public abstract class AbstractCircuitBreakerDecorator {

    CircuitBreakerConfig buildConfig(CommonCircuitBreakerConfigurationProperties.InstanceProperties properties) {
        CircuitBreakerConfig.Builder builder = CircuitBreakerConfig.custom();
        if (properties != null) {
            if (properties.getEnableExponentialBackoff() != null && properties.getEnableExponentialBackoff()
                    && properties.getEnableRandomizedWait() != null && properties.getEnableRandomizedWait()) {
                throw new IllegalStateException(
                        "you can not enable Exponential backoff policy and randomized delay at the same time , please enable only one of them");
            }
            if (properties.getFailureRateThreshold() != null) {
                builder.failureRateThreshold(properties.getFailureRateThreshold());
            }

            if (properties.getWritableStackTraceEnabled() != null) {
                builder.writableStackTraceEnabled(properties.getWritableStackTraceEnabled());
            }

            if (properties.getSlowCallRateThreshold() != null) {
                builder.slowCallRateThreshold(properties.getSlowCallRateThreshold());
            }

            if (properties.getSlowCallDurationThreshold() != null) {
                builder.slowCallDurationThreshold(properties.getSlowCallDurationThreshold());
            }

            if (properties.getMaxWaitDurationInHalfOpenState() != null) {
                builder.maxWaitDurationInHalfOpenState(properties.getMaxWaitDurationInHalfOpenState());
            }

            if (properties.getSlidingWindowSize() != null) {
                builder.slidingWindowSize(properties.getSlidingWindowSize());
            }

            if (properties.getMinimumNumberOfCalls() != null) {
                builder.minimumNumberOfCalls(properties.getMinimumNumberOfCalls());
            }

            if (properties.getSlidingWindowType() != null) {
                builder.slidingWindowType(properties.getSlidingWindowType());
            }

            if (properties.getPermittedNumberOfCallsInHalfOpenState() != null) {
                builder.permittedNumberOfCallsInHalfOpenState(
                        properties.getPermittedNumberOfCallsInHalfOpenState());
            }

            if (properties.getRecordExceptions() != null) {
                builder.recordExceptions(properties.getRecordExceptions());
                // if instance config has set recordExceptions, then base config's recordExceptionPredicate is useless.
                builder.recordException(null);
            }

            if (properties.getRecordFailurePredicate() != null) {
                buildRecordFailurePredicate(properties, builder);
            }

            if (properties.getRecordResultPredicate() != null) {
                buildRecordResultPredicate(properties, builder);
            }

            if (properties.getIgnoreExceptions() != null) {
                builder.ignoreExceptions(properties.getIgnoreExceptions());
                builder.ignoreException(null);
            }

            if (properties.getIgnoreExceptionPredicate() != null) {
                buildIgnoreExceptionPredicate(properties, builder);
            }

            if (properties.getAutomaticTransitionFromOpenToHalfOpenEnabled() != null) {
                builder.automaticTransitionFromOpenToHalfOpenEnabled(
                        properties.getAutomaticTransitionFromOpenToHalfOpenEnabled());
            }
        }
        return builder.build();
    }

    private void buildRecordFailurePredicate(CommonCircuitBreakerConfigurationProperties.InstanceProperties properties, CircuitBreakerConfig.Builder builder) {
        if (properties.getRecordFailurePredicate() != null) {
            Predicate<Throwable> predicate = ClassUtils.instantiatePredicateClass(properties.getRecordFailurePredicate());
            builder.recordException(predicate);
        }
    }

    private void buildRecordResultPredicate(CommonCircuitBreakerConfigurationProperties.InstanceProperties properties, CircuitBreakerConfig.Builder builder) {
        if (properties.getRecordResultPredicate() != null) {
            Predicate<Object> predicate = ClassUtils.instantiatePredicateClass(properties.getRecordResultPredicate());
            builder.recordResult(predicate);
        }
    }

    private void buildIgnoreExceptionPredicate(CommonCircuitBreakerConfigurationProperties.InstanceProperties properties, CircuitBreakerConfig.Builder builder) {
        if (properties.getIgnoreExceptionPredicate() != null) {
            Predicate<Throwable> predicate = ClassUtils.instantiatePredicateClass(properties.getIgnoreExceptionPredicate());
            builder.ignoreException(predicate);
        }
    }

    abstract CircuitBreaker getCircuitBreaker();

    abstract void doMethod();

    public void circuitBreakerMethod() {
        log.info("start do method.....");
        Decorators.ofRunnable(this::doMethod).withCircuitBreaker(getCircuitBreaker()).decorate().run();

        try {
            Decorators.ofCallable(()->"").withCircuitBreaker(getCircuitBreaker()).withFallback(throwable -> "").call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        log.info("end do method.....");
    }


    public void circuitBreaker(){
        Decorators.ofSupplier(()->{
            return "";
        }).withCircuitBreaker(getCircuitBreaker()).withFallback((s, throwable) -> {
            return "";
        }).decorate().get();
    }

}
