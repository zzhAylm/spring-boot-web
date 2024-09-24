package com.zzh.springboot3.config;

import com.fasterxml.jackson.core.StreamReadConstraints;
import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.HealthCheckHandler;
import com.netflix.discovery.EurekaClient;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.Node;
import org.redisson.api.NodesGroup;
import org.redisson.api.RedissonClient;
import org.redisson.connection.ConnectionListener;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationProperties;
import org.springframework.cloud.netflix.eureka.CloudEurekaInstanceConfig;
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaRegistration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/5/20 11:51
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({ConfigProperties.class,Resilience4jProperties.class})
public class SpringBoot3Config {

    @Resource
    private RedissonClient redisson;

    @PostConstruct
    public void init() {
        NodesGroup<Node> nodesGroup = redisson.getNodesGroup();
        nodesGroup.addConnectionListener(new ConnectionListener() {
            public void onConnect(InetSocketAddress addr) {
                // Redis节点连接成功
                log.info(addr.toString() + ":Redis节点连接成功");
            }

            public void onDisconnect(InetSocketAddress addr) {
                // Redis节点连接断开
                log.info(addr.toString() + ":Redis节点连接断开");
            }
        });
    }

    // 设置请求体的最大长度
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return (jacksonObjectMapperBuilder) -> jacksonObjectMapperBuilder.postConfigurer(objectMapper -> objectMapper.getFactory().setStreamReadConstraints(StreamReadConstraints.builder().maxNestingDepth(2000).maxStringLength(100_000_000).build()));
    }


    @Bean
    @org.springframework.cloud.context.config.annotation.RefreshScope
    @ConditionalOnBean(AutoServiceRegistrationProperties.class)
    @ConditionalOnProperty(value = "spring.cloud.service-registry.auto-registration.enabled", matchIfMissing = true)
    public EurekaRegistration eurekaRegistration(EurekaClient eurekaClient,
                                                 CloudEurekaInstanceConfig instanceConfig, ApplicationInfoManager applicationInfoManager,
                                                 @Autowired(required = false) ObjectProvider<HealthCheckHandler> healthCheckHandler) {
        return EurekaRegistration.builder(instanceConfig).with(applicationInfoManager).with(eurekaClient)
                .with(healthCheckHandler).build();
    }
}
