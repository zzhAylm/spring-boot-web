package com.zzh.springboot3.config;

import com.fasterxml.jackson.core.StreamReadConstraints;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.Node;
import org.redisson.api.NodesGroup;
import org.redisson.api.RedissonClient;
import org.redisson.connection.ConnectionListener;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
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


}
