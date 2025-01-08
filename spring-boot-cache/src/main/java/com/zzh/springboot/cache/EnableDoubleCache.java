package com.zzh.springboot.cache;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/11/5 20:45
 */
@Target(ElementType.TYPE)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Import(DoubleCachePointcutAdvisor.class)
public @interface EnableDoubleCache {

    String value() default "";
}
