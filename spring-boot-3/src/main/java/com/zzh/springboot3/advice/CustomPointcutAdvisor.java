package com.zzh.springboot3.advice;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/11/4 17:01
 */
@Slf4j
@Component
public class CustomPointcutAdvisor implements PointcutAdvisor {

    AnnotationMatchingPointcut pointcut = new AnnotationMatchingPointcut(CustomAnnotation.class);

    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }

    @Override
    public Advice getAdvice() {
        return (MethodInterceptor) MethodInvocation::getMethod;
    }
}
