package com.zzh.springboot3.config;

import io.github.resilience4j.common.circuitbreaker.configuration.CommonCircuitBreakerConfigurationProperties;
import io.github.resilience4j.retry.Retry;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/9/10 15:25
 */
@Data
@ConfigurationProperties(prefix = Resilience4jProperties.PREFIX)
public class Resilience4jProperties {
    public static final String PREFIX = "zzh.resilience4j";

    private Map<String, CommonCircuitBreakerConfigurationProperties.InstanceProperties> circuitBreaker = new HashMap<>();


    @Bean
    public Retry retry() {
        return Retry.ofDefaults("retry");
    }

}
