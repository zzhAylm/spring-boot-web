package com.zzh.kafka.consumer;

import com.zzh.kafka.constant.KafkaConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2025/1/2 10:44
 */
@Slf4j
@Configuration
public class  KafkaConsumer {

    @KafkaListener(topics = KafkaConstant.TOPIC_TEST,groupId = "group_topic_test")
    public void consumer(ConsumerRecord<String, String> record, Acknowledgment ack) {
        log.info("consumer message is :{}", record);
        ack.acknowledge();
    }

}
