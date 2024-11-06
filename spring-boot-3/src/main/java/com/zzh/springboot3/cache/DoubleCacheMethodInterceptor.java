package com.zzh.springboot3.cache;

import cn.hutool.json.JSONUtil;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.StringUtils;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/11/5 11:36
 */
@Slf4j
public class DoubleCacheMethodInterceptor implements MethodInterceptor {

    private static final String CACHE_NAME_DEFAULT = "default";
    private final RedissonClient redissonClient;

    private final Map<String, Cache<String, Object>> cacheManager;

    public DoubleCacheMethodInterceptor(RedissonClient redissonClient, Map<String, Cache<String, Object>> cacheManager) {
        this.redissonClient = redissonClient;
        this.cacheManager = cacheManager;
    }

    @Nullable
    @Override
    public Object invoke(@Nonnull MethodInvocation invocation) {
        Method method = invocation.getMethod();
        Object[] arguments = invocation.getArguments();
        Parameter[] parameters = method.getParameters();

        DoubleCacheEvict doubleCacheEvict = method.getAnnotation(DoubleCacheEvict.class);
        if (doubleCacheEvict != null) {
            String key = generateKey(doubleCacheEvict.key(),arguments,parameters);;
            if (StringUtils.isNotEmpty(key)) {
                String cacheName = StringUtils.defaultIfEmpty(doubleCacheEvict.value(), CACHE_NAME_DEFAULT);
                Cache<String, Object> localCache = cacheManager.get(cacheName);
                if (Objects.nonNull(localCache)) {
                    log.info("使用了一级缓存，cache evict cacheName is :{},key is :{}", cacheName, key);
                    localCache.invalidate(key);
                }
                RMap<Object, Object> redisCache = redissonClient.getMap(cacheName);
                log.info("使用了redis二级缓存，cache evict cacheName is :{},key is :{}", cacheName, key);
                redisCache.remove(key);
            }
        }


        DoubleCache doubleCache = method.getAnnotation(DoubleCache.class);
        if (doubleCache != null) {
            String key =  generateKey(doubleCache.key(),arguments,parameters);
            if (StringUtils.isNotEmpty(key)) {
                String cacheName = StringUtils.defaultIfEmpty(doubleCache.value(), CACHE_NAME_DEFAULT);
                Cache<String, Object> localCache = cacheManager.get(cacheName);
                if (Objects.nonNull(localCache)) {
                    Object cacheObj = localCache.getIfPresent(key);
                    if (Objects.nonNull(cacheObj)) {
                        log.info("使用了一级缓存，cache get ,cacheName is {},key is :{},obj is :{}", cacheName, key, JSONUtil.toJsonStr(cacheObj));
                        return cacheObj;
                    }
                }
                RMap<Object, Object> redisCache = redissonClient.getMap(cacheName);
                Object redisObj = redisCache.get(key);
                if (Objects.nonNull(redisObj)) {
                    log.info("使用了Redis 二级缓存，cache get ,cacheName is {},key is :{},obj is :{}", cacheName, key, JSONUtil.toJsonStr(redisObj));
                    if (Objects.nonNull(localCache)) {
                        log.info("二级缓存放入一级缓存中，double cache put ,cacheName is {},key is :{},obj is :{}", cacheName, key, JSONUtil.toJsonStr(redisObj));
                        localCache.put(key, redisObj);
                    }
                    return redisObj;
                }
            }
        }
        Object proceed;
        try {
            proceed = invocation.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        if (doubleCache != null && Objects.nonNull(proceed)) {
            String key =  generateKey(doubleCache.key(),arguments,parameters);
            if (StringUtils.isNotEmpty(key)) {
                String cacheName = StringUtils.defaultIfEmpty(doubleCache.value(), CACHE_NAME_DEFAULT);
                Cache<String, Object> localCache = cacheManager.get(cacheName);
                if (Objects.nonNull(localCache)) {
                    log.info("使用了一级缓存，DoubleCache put cacheName is :{},key is :{},obj is :{}", cacheName, key, JSONUtil.toJsonStr(proceed));
                    localCache.put(key, proceed);
                }
                RMap<Object, Object> redisCache = redissonClient.getMap(cacheName);
                log.info("使用了redis二级缓存，DoubleCache put cacheName is :{},key is :{},obj is :{}", cacheName, key, JSONUtil.toJsonStr(proceed));
                redisCache.put(key, proceed);
            }
        }


        DoubleCachePut doubleCachePut = method.getAnnotation(DoubleCachePut.class);
        if (doubleCachePut != null && doubleCache == null && Objects.nonNull(proceed)) {
            String cacheName = StringUtils.defaultIfEmpty(doubleCachePut.value(), CACHE_NAME_DEFAULT);
            String key = generateKey(doubleCachePut.key(),invocation.getArguments(),method.getParameters());
            if (StringUtils.isNotEmpty(key)) {
                Cache<String, Object> localCache = cacheManager.get(cacheName);
                if (Objects.nonNull(localCache)) {
                    log.info("使用了一级缓存，cache put cacheName is :{},key is :{},obj is :{}", cacheName, key, JSONUtil.toJsonStr(proceed));
                    localCache.put(key, proceed);
                }
                RMap<Object, Object> redisCache = redissonClient.getMap(cacheName);
                log.info("使用了redis二级缓存，cache put cacheName is :{},key is :{},obj is :{}", cacheName, key, JSONUtil.toJsonStr(proceed));
                redisCache.put(key, proceed);
            }
        }
        return proceed;
    }

    public static String generateKey(String key, Object[] arguments, Parameter[] parameters) {
        Map<String, Object> params = new HashMap<>();
        for (int i = 0; i < parameters.length; i++) {
            params.put(parameters[i].getName(), arguments[i]);
        }
        return parser(key, params);
    }

    public static String parser(String elString, Map<String, Object> params) {
        elString = String.format("#{%s}", elString);
        //创建表达式解析器
        ExpressionParser parser = new SpelExpressionParser();
        //通过evaluationContext.setVariable可以在上下文中设定变量。
        EvaluationContext context = new StandardEvaluationContext();
        params.forEach(context::setVariable);
        //解析表达式
        Expression expression = parser.parseExpression(elString, new TemplateParserContext());
        //使用Expression.getValue()获取表达式的值，这里传入了Evaluation上下文
        return expression.getValue(context, String.class);
    }


}
