package com.zzh.springboot3.config;

import cn.hutool.core.io.resource.ResourceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;


/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/11/22 15:13
 */
@Slf4j
@Component
public class ActuatorPropertiesEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private static final String ACTUATOR_PROPERTIES = "actuator-properties.yml";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {

        YamlPropertySourceLoader yamlPropertySourceLoader = new YamlPropertySourceLoader();
        try {
            List<PropertySource<?>> load = yamlPropertySourceLoader.load(ACTUATOR_PROPERTIES, new ClassPathResource(ACTUATOR_PROPERTIES));
            load.forEach(propertySource -> environment.getSystemEnvironment().put(propertySource.getName(), propertySource.getSource()));
            log.info("加载Actuator配置文件成功");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
