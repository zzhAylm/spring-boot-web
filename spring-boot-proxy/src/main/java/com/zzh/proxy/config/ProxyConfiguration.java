package com.zzh.proxy.config;

import com.zzh.proxy.service.CustomInvocationHandler;
import com.zzh.proxy.service.ProxyService;
import com.zzh.proxy.service.ProxyServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Proxy;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/12/26 17:20
 */
@Configuration
public class ProxyConfiguration {


    @Bean
    public ProxyService proxyService() {
        ProxyService proxyService = new ProxyServiceImpl();
        return (ProxyService) Proxy.newProxyInstance(proxyService.getClass().getClassLoader(), proxyService.getClass().getInterfaces(), new CustomInvocationHandler<>(proxyService));
    }


}
