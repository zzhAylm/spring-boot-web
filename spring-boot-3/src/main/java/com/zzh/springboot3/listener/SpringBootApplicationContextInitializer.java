package com.zzh.springboot3.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/9/25 12:14
 */
@Slf4j
public class SpringBootApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        log.info("SpringBootApplicationContextInitializer is initalize");
    }
}
