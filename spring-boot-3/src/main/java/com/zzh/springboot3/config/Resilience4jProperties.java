package com.zzh.springboot3.config;

import io.github.resilience4j.common.circuitbreaker.configuration.CommonCircuitBreakerConfigurationProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

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
    public static final String PREFIX = "com.zzh.resilience4j";

    private Map<String, CommonCircuitBreakerConfigurationProperties.InstanceProperties> circuitBreaker = new HashMap<>();


}
