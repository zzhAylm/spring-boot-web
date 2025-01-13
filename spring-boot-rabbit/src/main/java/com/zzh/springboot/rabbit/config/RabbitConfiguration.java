package com.zzh.springboot.rabbit.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2025/1/13 11:15
 */
@Slf4j
@Configuration
public class RabbitConfiguration {


    @RabbitListener(queues = "fanout.zzh_queue")
    public void messageListenerFanout(String message) {
        log.info("rabbit fanout consumer message is : {}", message);
    }

    @RabbitListener(queues = "direct.zzh_queue")
    public void messageListenerDirect(String message) {
        log.info("rabbit direct consumer message is : {}", message);
    }

    @RabbitListener(queues = "direct.zzh_queue_routing_key")
    public void messageListenerDirectRoutingKey(String message) {
        log.info("rabbit direct routing key consumer message is : {}", message);
    }

    @RabbitListener(queues = "topic.zzh_queue")
    public void messageListenerTopic(String message) {
        log.info("rabbit topic consumer message is : {}", message);
    }

    @RabbitListener(queues = "topic.zzh_queue_routing_key_1")
    public void messageListenerTopicRoutingKey1(String message) {
        log.info("rabbit topic routing key 1 consumer message is : {}", message);
    }

    @RabbitListener(queues = "topic.zzh_queue_routing_key_2")
    public void messageListenerTopicRoutingKey2(String message) {
        log.info("rabbit topic routing key 2 consumer message is : {}", message);
    }

    @RabbitListener(queues = "topic.zzh_queue_routing_keys")
    public void messageListenerTopicRoutingKeys(String message) {
        log.info("rabbit topic routing keys consumer message is : {}", message);
    }

}
