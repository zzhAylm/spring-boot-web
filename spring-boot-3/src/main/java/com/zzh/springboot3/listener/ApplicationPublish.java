package com.zzh.springboot3.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/9/25 11:35
 */
@Slf4j
@Component
public class ApplicationPublish implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher applicationEventPublisher;


    public <R extends ApplicationEvent> void publishEvent(R applicationEvent) {
        applicationEventPublisher.publishEvent(applicationEvent);
    }


    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
