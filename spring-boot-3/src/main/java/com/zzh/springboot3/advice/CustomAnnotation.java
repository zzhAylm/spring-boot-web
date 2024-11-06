package com.zzh.springboot3.advice;

import java.lang.annotation.*;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/11/4 16:23
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomAnnotation {
    String value() default "";
}
