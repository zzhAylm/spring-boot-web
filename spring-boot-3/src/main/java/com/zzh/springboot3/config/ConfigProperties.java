package com.zzh.springboot3.config;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/7/5 16:21
 */
@Data
@ConfigurationProperties(prefix = ConfigProperties.PREFIX)
public class ConfigProperties implements EnvironmentAware {

    public static final String PREFIX = "spring.config";

    private Environment environment;

    /**
     * 构造柱入，需要显示指定@Autowired
     */
    @Autowired
    public ConfigProperties(Environment environment) {
        this.environment = environment;
    }


    @PostConstruct
    public void init() {
        System.out.println(environment);
    }
}
