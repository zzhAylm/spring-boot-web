package com.zzh.pattern.chain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 责任链模式
 * @Author: zzh
 * @Crete 2024/12/23 20:32
 */
@Slf4j
@Component
public class ResponsibilityChain implements CommandLineRunner, ApplicationContextAware {

    private final List<AbstractHandler> handlers = new ArrayList<>();

    private ApplicationContext applicationContext;

    public void handler(RequestParam param) {
        handlers.forEach(handler -> {
            handler.handler(param);
        });
    }


    @Override
    public void run(String... args) throws Exception {
        Map<String, AbstractHandler> handlerMap = applicationContext.getBeansOfType(AbstractHandler.class);
        handlerMap.forEach((beanName, handler) -> {
            handlers.add(handler);
        });
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
