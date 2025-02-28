package com.zzh.kafka.service;

import com.zzh.kafka.constant.KafkaConstant;
import com.zzh.springboot3.common.utils.JsonUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2025/1/2 10:04
 */
@Slf4j
@Service
@SpringBootTest
public class KafkaServiceTest {

    @Resource
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Test
    public void sendMessage() {
        String message = "zzh_message";
        kafkaTemplate.send(KafkaConstant.TOPIC_TEST, message);
    }


    @Test
    public void sendMessageCallback() {
        for (int i = 0; i < 10; i++) {
            String message = "zzh_message";
            CompletableFuture<SendResult<String, Object>> send = kafkaTemplate.send(KafkaConstant.TOPIC_TEST, String.valueOf(i));
            send.whenComplete((t, u) -> {
                if (u != null) {
                    u.printStackTrace();
                    return;
                }
                RecordMetadata recordMetadata = t.getRecordMetadata();
                ProducerRecord<String, Object> producerRecord = t.getProducerRecord();
                log.info("key is :{},value is :{},topic is :{},partition is：{}", producerRecord.key(), producerRecord.value(), recordMetadata.topic(), recordMetadata.partition());
            });
        }

    }


    @Test
    public void sendMsgSync() throws ExecutionException, InterruptedException, TimeoutException {
        // 同步发送：get的时候就是阻塞等待sender线程将消息发送给broker
        // 这样无法使用，kafka 发送缓冲区，实现的批量发送
        String message = "zzh_message";
        SendResult<String, Object> stringObjectSendResult = kafkaTemplate.send(KafkaConstant.TOPIC_TEST, message).get(10, TimeUnit.SECONDS);
        log.info("sync send msg ,result is :{}", stringObjectSendResult);
    }

    @Test
    public void sendMsgASync() throws ExecutionException, InterruptedException, TimeoutException {
        // 异步发送：同一个topic使用同一个producer
        // 可以重新利用 kafka生产者的缓冲区，实现批量发送
        // KafkaTemplate.send()方法 底层使用的都是一个KafkaProducer对象，同一个缓冲区，同一个sender线程
        //
        String message = "zzh_message";
        CompletableFuture<SendResult<String, Object>> completableFuture = kafkaTemplate.send(KafkaConstant.TOPIC_TEST, message);
        completableFuture.whenComplete((t, u) -> {
            if (u != null) {
                u.printStackTrace();
                return;
            }
            RecordMetadata recordMetadata = t.getRecordMetadata();
            ProducerRecord<String, Object> producerRecord = t.getProducerRecord();
            log.info("key is :{},value is :{},topic is :{},partition is：{}", producerRecord.key(), producerRecord.value(), recordMetadata.topic(), recordMetadata.partition());
        });

        CompletableFuture<SendResult<String, Object>> completableFuture1 = kafkaTemplate.send("zzh_topic", message);
        completableFuture1.whenComplete((t, u) -> {
            if (u != null) {
                u.printStackTrace();
                return;
            }
            RecordMetadata recordMetadata = t.getRecordMetadata();
            ProducerRecord<String, Object> producerRecord = t.getProducerRecord();
            log.info("key is :{},value is :{},topic is :{},partition is：{}", producerRecord.key(), producerRecord.value(), recordMetadata.topic(), recordMetadata.partition());
        });
    }
}
