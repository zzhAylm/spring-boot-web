package com.zzh.kafka.consumer;

import com.zzh.kafka.constant.KafkaConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.retry.annotation.Backoff;

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
//        if (record.value().equals("2")){
//            throw new RuntimeException("test");
//        }
        ack.acknowledge();
    }

    // 重试 5 次，重试间隔 100 毫秒,最大间隔 1 秒
    @RetryableTopic(
            attempts = "5",
            backoff = @Backoff(delay = 100, maxDelay = 1000)
    )
    @KafkaListener(topics =  KafkaConstant.TOPIC_TEST, groupId = "apple")
    private void customer(ConsumerRecord<String, String> record, Acknowledgment ack) {
        log.info("kafka customer:{}", record);
        Integer n = Integer.parseInt(record.value());
        if (n % 5 == 0) {
            throw new RuntimeException();
        }
        System.out.println(n);
    }

}
