package com.zzh.springboot3.advice;

import cn.hutool.json.JSONUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.shardingsphere.transaction.annotation.ShardingSphereTransactionType;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/11/4 17:01
 */
@Slf4j
@Component
public class CustomPointcutAdvisor implements PointcutAdvisor {


    @Override
    public Pointcut getPointcut() {
        ComposablePointcut classAnnotation = new ComposablePointcut(AnnotationMatchingPointcut.forClassAnnotation(CustomAnnotation.class));
        ComposablePointcut methodAnnotation = new ComposablePointcut(AnnotationMatchingPointcut.forMethodAnnotation(CustomAnnotation.class));
        return methodAnnotation.union(classAnnotation);
    }

    @Override
    public Advice getAdvice() {
        return new MethodInterceptor() {
            @Nullable
            @Override
            public Object invoke(@Nonnull MethodInvocation invocation) throws Throwable {
                Object proceed = invocation.proceed();
                ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)(RequestContextHolder.getRequestAttributes());
                assert servletRequestAttributes != null;
                HttpServletRequest request = servletRequestAttributes.getRequest();
                HttpServletResponse response = servletRequestAttributes.getResponse();
                log.info("request is :{}", request);
                log.info("request URI is :{}",request.getRequestURI());
                log.info("response is :{}", response);
                assert response != null;
                int status = response.getStatus();
                log.info("response status is :{}", status);
                return proceed;
            }
        };
    }
}
