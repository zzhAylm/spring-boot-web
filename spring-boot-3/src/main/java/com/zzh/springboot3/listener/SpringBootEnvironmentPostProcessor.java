package com.zzh.springboot3.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/9/25 12:14
 */
@Slf4j
public class SpringBootEnvironmentPostProcessor implements EnvironmentPostProcessor {
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        log.info("SpringBootEnvironmentPostProcessor is running");
    }
}
