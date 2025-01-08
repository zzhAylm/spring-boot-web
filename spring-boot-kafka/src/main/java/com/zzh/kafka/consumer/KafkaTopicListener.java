package com.zzh.kafka.consumer;

import com.zzh.kafka.constant.KafkaConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2025/1/7 10:11
 */
@Slf4j
@Component
@KafkaListener(topics = KafkaConstant.TOPIC_TEST, groupId = "test_topic_group_listener")
public class KafkaTopicListener {

    @KafkaHandler
    public void handleMessage_1(String message) {
        log.info("handleMessage_1 consumer message:{} ", message);
    }

    @KafkaHandler(isDefault = true)
    public void handleMessage(String message) {
        log.info("default message consumer message is :{} ", message);
    }


}
