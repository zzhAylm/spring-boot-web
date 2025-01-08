package com.zzh.springboot.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2025/1/8 11:18
 */
@Configuration
@EnableConfigurationProperties(DoubleProperties.class)
public class CacheConfiguration {
}
