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
        String message = "zzh_message";
        CompletableFuture<SendResult<String, Object>> send = kafkaTemplate.send(KafkaConstant.TOPIC_TEST, message);
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


    @Test
    public void sendMsgSync() throws ExecutionException, InterruptedException, TimeoutException {
        // 同步发送：
        String message = "zzh_message";
        SendResult<String, Object> stringObjectSendResult = kafkaTemplate.send(KafkaConstant.TOPIC_TEST, message).get(10, TimeUnit.SECONDS);
        log.info("sync send msg ,result is :{}", stringObjectSendResult);
    }

    @Test
    public void sendMsgASync() throws ExecutionException, InterruptedException, TimeoutException {
        // 异步发送：
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
    }
}
