package com.zzh.springboot.cache;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 二级缓存注解
 * @Author: zzh
 * @Crete 2024/11/5 11:11
 */
@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface DoubleCacheEvict {

    @AliasFor("cacheName")
    String value() default "";

    String key() default "";

    @AliasFor("value")
    String cacheName() default "";


    long expire() default 120;

    TimeUnit timeUnit() default TimeUnit.SECONDS;


}
