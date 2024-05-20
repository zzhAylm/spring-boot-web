package com.zzh.springboot3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/5/20 15:15
 */
@Configuration
public class WebSecurityConfig {
    @Bean
    public WebSecurityCustomizer ignoringCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/test/**", "/static/**","/*");
    }
}
