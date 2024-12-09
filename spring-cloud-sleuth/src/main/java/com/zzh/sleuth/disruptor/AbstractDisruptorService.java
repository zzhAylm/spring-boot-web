package com.zzh.sleuth.disruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.apache.hc.core5.concurrent.DefaultThreadFactory;

import java.util.Objects;
import java.util.concurrent.ThreadFactory;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/12/6 14:30
 */
public abstract class AbstractDisruptorService<T> implements DisruptorService<T>, AutoCloseable {

    private final RingBuffer<Event<T>> ringBuffer;
    private final Disruptor<Event<T>> disruptor;

    public AbstractDisruptorService() {
        this.disruptor = new Disruptor<>(eventFactory(), ringBufferSize(), threadFactory(), producerType(), new BlockingWaitStrategy());
        this.ringBuffer = this.disruptor.getRingBuffer();
        this.disruptor.handleEventsWithWorkerPool(eventHandler());
        if (Objects.nonNull(eventHandlers())) {
            this.disruptor.handleEventsWithWorkerPool();
        }
        this.disruptor.start();
    }


    @Override
    public void publish(T data) {
        long next = ringBuffer.next();
        Event<T> event = ringBuffer.get(next);
        event.setData(data);
        ringBuffer.publish(next);
    }

    @Override
    public Integer ringBufferSize() {
        return 1024;
    }

    @Override
    public ProducerType producerType() {
        return ProducerType.SINGLE;
    }

    @Override
    public EventFactory<Event<T>> eventFactory() {
        return Event::new;
    }


    @Override
    public ThreadFactory threadFactory() {
        return new DefaultThreadFactory("disruptor");
    }

    @Override
    public WorkHandler<Event<T>>[] eventHandlers() {
        return null;
    }

    @Override
    public void close() throws Exception {
        disruptor.shutdown();
    }
}
