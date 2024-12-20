package com.zzh.mvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/12/20 14:18
 */
@Configuration
public class MvcConfiguration implements WebMvcConfigurer {


    @Bean
    public CustomHandlerMethodArgumentResolver customHandlerMethodArgumentResolver() {
        return new CustomHandlerMethodArgumentResolver();
    }

    @Bean
    public CustomInterceptor customInterceptor() {
        return new CustomInterceptor();
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(customHandlerMethodArgumentResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(customInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/login", "/notFound");
    }
}
