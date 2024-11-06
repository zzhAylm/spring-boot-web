package com.zzh.springboot3.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.zzh.springboot3.config.DubboProperties;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.Data;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.redisson.api.RedissonClient;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/11/5 11:18
 */
@Data
public class DoubleCachePointcutAdvisor implements PointcutAdvisor {

    private Pointcut pointcut;

    private MethodInterceptor advice;

    @Resource
    private RedissonClient redissonClient;

    private Map<String, Cache<String, Object>> cacheManager = new HashMap<>();

    @Resource
    private DubboProperties dubboProperties;


    @PostConstruct
    public void init() {
        pointcut = new DoubleCacheMethodMatcherPointcut();

        Map<String, DubboProperties.CacheProperties> cachePropertiesMap = dubboProperties.getCacheManager();

        cacheManager.put("default", Caffeine.newBuilder()
                .initialCapacity(1024)
                .build());
        cachePropertiesMap.forEach((key, value) -> {
            Cache<String, Object> cache = Caffeine.newBuilder()
                    .initialCapacity(value.getCapacity())
                    .build();
            cacheManager.put(key, cache);
        });
        advice = new DoubleCacheMethodInterceptor(redissonClient, cacheManager);
    }

    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }
}
