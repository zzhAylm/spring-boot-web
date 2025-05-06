package com.zzh.springboot.disruptor.disruptor;

import com.lmax.disruptor.EventHandler;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import static com.zzh.springboot.disruptor.controller.DisruptorController.KEY_HASH;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/12/6 14:44
 */
@Slf4j
@Service
public class StringDisruptorService extends AbstractDisruptorService<String> {

    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Override
    public EventHandler<Event<String>> eventHandler() {
        return (event, sequence, endOfBatch) -> {
            String data = event.getData();
            redisTemplate.opsForHash().delete(KEY_HASH, data);
            log.info("消费消息，sequence is :{},endOfBatch is :{} event is :{}", sequence, endOfBatch, data);
        };
    }
}
