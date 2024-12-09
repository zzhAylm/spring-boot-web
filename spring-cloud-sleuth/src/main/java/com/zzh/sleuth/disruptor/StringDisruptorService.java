package com.zzh.sleuth.disruptor;

import com.lmax.disruptor.WorkHandler;
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
    public WorkHandler<Event<String>> eventHandler() {
        return event -> {
            String data = event.getData();
            log.info("消费消息，event is :{}", data);
        };
    }
}
