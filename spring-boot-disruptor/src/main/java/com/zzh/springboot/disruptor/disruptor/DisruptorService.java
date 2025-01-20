package com.zzh.springboot.disruptor.disruptor;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.ThreadFactory;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/12/6 14:23
 */
public interface DisruptorService<T> {

    void publish(T data);

    EventHandler<Event<T>> eventHandler();

    EventHandler<Event<T>>[] eventHandlers();

    Integer ringBufferSize();

    ProducerType producerType();

    EventFactory<Event<T>> eventFactory();

    ThreadFactory threadFactory();
}
