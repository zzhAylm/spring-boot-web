package com.zzh.springboot.resilience4j.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2025/1/8 10:49
 */
@Configuration
@EnableConfigurationProperties(Resilience4jProperties.class)
public class Resilience4jConfig {

}
