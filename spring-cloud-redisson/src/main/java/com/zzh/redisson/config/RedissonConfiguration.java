package com.zzh.redisson.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.api.RedissonRxClient;
import org.redisson.client.RedisClient;
import org.redisson.codec.JsonJacksonCodec;
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
        Config config = new Config();
        config.useSingleServer().setAddress("redis://localhost:6379");
        // 创建JsonJacksonCodec对象，并设置为Redisson的默认编码解码器，不配置的话，你的数字和字段会基于二进制存储，不方便查看
        JsonJacksonCodec codec = new JsonJacksonCodec();
        config.setCodec(codec);
        return Redisson.create(config);
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
