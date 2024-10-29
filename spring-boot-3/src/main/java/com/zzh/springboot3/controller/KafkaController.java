package com.zzh.springboot3.controller;

import cn.hutool.json.JSONUtil;
import com.google.common.util.concurrent.ListenableFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/10/24 10:25
 */
@Slf4j
@RestController
@RequestMapping("/kafka")
public class KafkaController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC = "topic_test";

    @RequestMapping("/{msg}")
    public void product(@PathVariable String msg) {
        CompletableFuture<SendResult<String, String>> completableFuture = kafkaTemplate.send(TOPIC, msg);
        SendResult<String, String> stringStringSendResult;
        try {
            stringStringSendResult = completableFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        log.info("send result is :{}", JSONUtil.toJsonStr(stringStringSendResult));
    }

    @RequestMapping("/callback/{msg}")
    public void product2(@PathVariable String msg) {
        CompletableFuture<SendResult<String, String>> completableFuture = kafkaTemplate.send(TOPIC, msg);
        completableFuture.whenCompleteAsync((sendResult, throwable) -> {
            if (throwable != null) {
                // 发送失败处理
                log.error("Failed to send message: " + throwable.getMessage());
            } else {
                // 发送成功处理
                log.info("Message sent successfully:{}", sendResult.getRecordMetadata().toString());
            }
        });
    }
}
