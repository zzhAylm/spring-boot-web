package com.zzh.sleuth.disruptor;

import cn.hutool.json.JSONUtil;
import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventProcessorFactory;
import com.lmax.disruptor.dsl.ProducerType;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.concurrent.DefaultThreadFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/12/6 13:47
 */
@Slf4j
@Configuration
public class DisruptorConfig {


    public static void main(String[] args) throws InterruptedException {

        Disruptor<Event> disruptor = new Disruptor<>(Event::new, 1024, new DefaultThreadFactory("disruptor"), ProducerType.SINGLE, new BlockingWaitStrategy());

        disruptor.handleEventsWithWorkerPool(event -> {
            log.info("event is :{}", JSONUtil.toJsonStr(event));
        });

        disruptor.start();

        RingBuffer<Event> ringBuffer = disruptor.getRingBuffer();

        for (int i = 0; i < 1000; i++) {
            long next = ringBuffer.next();
            try {
                Event event = ringBuffer.get(next);
                event.setData("时间：" + System.currentTimeMillis() + "，线程：" + Thread.currentThread() + "，set event");
            } finally {
                ringBuffer.publish(next);
            }
            Thread.sleep(100);
        }

        disruptor.shutdown();

    }




}
