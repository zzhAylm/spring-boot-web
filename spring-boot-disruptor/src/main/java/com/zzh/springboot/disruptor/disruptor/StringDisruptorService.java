package com.zzh.springboot.disruptor.disruptor;

import com.lmax.disruptor.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/12/6 14:44
 */
@Slf4j
@Service
public class StringDisruptorService extends AbstractDisruptorService<String> {

    @Override
    public EventHandler<Event<String>> eventHandler() {
        return (event, sequence, endOfBatch) -> {
            String data = event.getData();
            log.info("消费消息，sequence is :{},endOfBatch is :{} event is :{}", sequence, endOfBatch, data);
        };
    }
}
