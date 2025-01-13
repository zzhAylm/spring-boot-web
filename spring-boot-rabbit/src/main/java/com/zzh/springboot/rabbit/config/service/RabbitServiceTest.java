package com.zzh.springboot.rabbit.config.service;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2025/1/13 11:45
 */
@Slf4j
@Service
@SpringBootTest
public class RabbitServiceTest {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Test
    public void sendFanoutMessageTest() {
        String message = "hello,rabbit";
        rabbitTemplate.send("fanout.zzh_exchange", null, new Message(message.getBytes()));

    }
    @Test
    public void sendDirectMessageTest() {
        String message = "hello,rabbit";
        rabbitTemplate.send("direct.zzh_exchange", null, new Message(message.getBytes()));

    }

    @Test
    public void sendDirectRoutingKeyMessageTest() {
        String message = "hello,rabbit";
        rabbitTemplate.send("direct.zzh_exchange", "zzh", new Message(message.getBytes()));
    }

    @Test
    public void sendTopicMessageTest() {
        String message = "hello,rabbit";
        rabbitTemplate.send("topic.zzh_exchange", null, new Message(message.getBytes()));
    }

    @Test
    public void sendTopicRoutingKey1MessageTest() {
        String message = "hello,rabbit";
        rabbitTemplate.send("topic.zzh_exchange", "zzh1", new Message(message.getBytes()));
    }

    @Test
    public void sendTopicRoutingKey2MessageTest() {
        String message = "hello,rabbit";
        rabbitTemplate.send("topic.zzh_exchange", "zzh2", new Message(message.getBytes()));
    }


    @Test
    public void sendTopicRoutingKeysMessageTest() {
        String message = "hello,rabbit";
        rabbitTemplate.send("topic.zzh_exchange", "zzh.info", new Message(message.getBytes()));
    }

    @Test
    public void sendTopicRoutingKeys2MessageTest() {
        String message = "hello,rabbit";
        rabbitTemplate.send("topic.zzh_exchange", "zzh.name", new Message(message.getBytes()));
    }

}
