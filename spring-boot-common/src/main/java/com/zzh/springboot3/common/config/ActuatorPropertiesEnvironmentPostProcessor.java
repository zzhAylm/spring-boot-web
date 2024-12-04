package com.zzh.springboot3.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;


/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/11/22 15:13
 */
public class ActuatorPropertiesEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private static final Logger log = LoggerFactory.getLogger(ActuatorPropertiesEnvironmentPostProcessor.class);

    private static final String ACTUATOR_PROPERTIES = "actuator-properties.yml";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {

        YamlPropertySourceLoader yamlPropertySourceLoader = new YamlPropertySourceLoader();
        try {
            List<PropertySource<?>> load = yamlPropertySourceLoader.load(ACTUATOR_PROPERTIES, new ClassPathResource(ACTUATOR_PROPERTIES));
            if (CollectionUtils.isEmpty(load)) {
                return;
            }
            MutablePropertySources propertySources = environment.getPropertySources();
            load.forEach(propertySources::addLast);
            log.info("加载Actuator配置文件成功");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
