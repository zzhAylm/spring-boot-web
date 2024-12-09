package com.zzh.sleuth.lock;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/12/6 16:07
 */
@Configuration
public class DistributedLockConfig {

    @Bean(destroyMethod = "shutdown")
    @ConditionalOnMissingBean(RedissonClient.class)
    public RedissonClient redissonClient() {
        return Redisson.create();
    }


}
