package com.zzh.redisson.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.api.RedissonRxClient;
import org.redisson.client.RedisClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/12/16 20:54
 */
@Configuration
public class RedissonConfiguration {

    @Bean
    public RedissonClient redissonClient() {
        return Redisson.create();
    }

    @Bean
    public RedissonRxClient redissonRxClient(RedissonClient redisClient) {
        return redisClient.rxJava();
    }

    @Bean
    public RedissonReactiveClient redissonReactiveClient(RedissonClient redisClient) {
        return redisClient.reactive();
    }

}
