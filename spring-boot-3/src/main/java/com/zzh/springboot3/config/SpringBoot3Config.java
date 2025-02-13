package com.zzh.springboot3.config;

import com.fasterxml.jackson.core.StreamReadConstraints;
import com.zzh.springboot3.service.ScopeService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.Node;
import org.redisson.api.NodesGroup;
import org.redisson.api.RedissonClient;
import org.redisson.connection.ConnectionListener;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.net.InetSocketAddress;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/5/20 11:51
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({ConfigProperties.class})
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


//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, String> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
////        factory.setConsumerFactory();
//        factory.setConcurrency(3); // 设置并发数
//        factory.getContainerProperties().setPollTimeout(3000);
//        return factory;
//    }


    @Bean
    @Scope("prototype")
    public ScopeService scopeService() {
        return new ScopeService();
    }

}
