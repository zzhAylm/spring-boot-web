package com.zzh.springboot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/11/5 14:25
 */
@Data
@ConfigurationProperties(prefix = DoubleProperties.PREFIX)
public class DoubleProperties implements Serializable {

    public static final String PREFIX = "zzh.double-cache";

    private Map<String, CacheProperties> cacheManager = new HashMap<>();

    @Data
    public static class CacheProperties implements Serializable {
        private Integer capacity = 1024;
    }
}
