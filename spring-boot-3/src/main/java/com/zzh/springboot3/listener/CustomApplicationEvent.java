package com.zzh.springboot3.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/9/25 11:45
 */
@Slf4j
public class CustomApplicationEvent extends ApplicationEvent {

    public CustomApplicationEvent(Object source) {
        super(source);
    }
}
