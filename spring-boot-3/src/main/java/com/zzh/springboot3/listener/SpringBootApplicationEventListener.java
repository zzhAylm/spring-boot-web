package com.zzh.springboot3.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.GenericApplicationListener;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/9/25 11:27
 */
@Slf4j
@Component
public class SpringBootApplicationEventListener implements GenericApplicationListener {
    @Override
    public boolean supportsEventType(ResolvableType eventType) {
        return true;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        log.debug("application event is appear , event is {}", event.getSource());
    }
}
