package com.zzh.sleuth.disruptor;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.ThreadFactory;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/12/6 14:23
 */
public interface DisruptorService<T> {

    void publish(T data);

    WorkHandler<Event<T>> eventHandler();

    WorkHandler<Event<T>>[] eventHandlers();

    Integer ringBufferSize();

    ProducerType producerType();

    EventFactory<Event<T>> eventFactory();

    ThreadFactory threadFactory();
}
