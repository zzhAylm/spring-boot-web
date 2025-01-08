package com.zzh.springboot.cache;

import org.springframework.aop.support.StaticMethodMatcherPointcut;

import java.lang.reflect.Method;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/11/5 11:37
 */
public class DoubleCacheMethodMatcherPointcut extends StaticMethodMatcherPointcut {
    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return method.isAnnotationPresent(DoubleCache.class) || method.isAnnotationPresent(DoubleCachePut.class) || method.isAnnotationPresent(DoubleCacheEvict.class);
    }
}
