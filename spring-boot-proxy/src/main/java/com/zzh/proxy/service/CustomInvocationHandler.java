package com.zzh.proxy.service;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/12/26 17:18
 */
@Slf4j
public class CustomInvocationHandler<T> implements InvocationHandler {

    private final T target;

    public CustomInvocationHandler(T target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("proxy : before method,proxy is :{}",proxy);
        return method.invoke(target, args);
    }
}
