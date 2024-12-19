package com.zzh.pattern.proxy;

/**
 * @Description: 代理模式
 * @Author: zzh
 * @Crete 2024/12/19 15:35
 */

import lombok.extern.slf4j.Slf4j;

/**
 * 装饰者模式 ： 侧重动态扩展对象的功能，多个装饰着动态组合，实现动态扩展
 * 代理模式：侧重控制对象的访问，通过代理对象来控制真是对象的访问
 * 简而言之，装饰者模式专注于 功能扩展，而代理模式专注于 访问控制。
 */
@Slf4j
public class SubjectProxy implements Subject {

    private Subject subject;

    public void proxy() {
        if (subject == null) {
            subject = new SubjectImpl();
        }
        log.info("proxy subject start");
        subject.proxy();
        log.info("proxy subject end");
    }
}
