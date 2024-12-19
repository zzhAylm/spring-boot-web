package com.zzh.pattern.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/12/19 17:25
 */
@Configuration
public class DesignPatternConfig {


    @Bean
    public List<String> designPatterBeans() {
        List<String> designPatterBeans = new ArrayList<>();
        designPatterBeans.add("factory");
        designPatterBeans.add("adaptor");
        designPatterBeans.add("singleton");
        designPatterBeans.add("proxy");
        designPatterBeans.add("decorator");
        return designPatterBeans;
    }

}
