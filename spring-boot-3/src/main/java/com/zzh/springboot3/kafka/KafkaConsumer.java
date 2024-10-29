package com.zzh.springboot3.kafka;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.eclipse.parsson.JsonUtil;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/**
 * 消费者
 * kafka监听器
 */
@Slf4j
@Component
public class KafkaConsumer {

    /**
     * kafka的监听器1，topic为"topic_test"，消费者组为"group_topic_test"
     *
     * @param record
     * @param item
     */
    @KafkaListener(topics = "topic_test", groupId = "group_topic_test")
    public void topicListener1(ConsumerRecord<String, String> record, Acknowledgment item) {
        String value = record.value();
        log.info("topicListener1 value is :{}", value);
        log.info("topicListener1 record is :{}", record);
        //手动提交
        item.acknowledge();
    }

    /**
     * kafka的监听器2，topic为"topic_test"，消费者组为"group_topic_test"
     *
     * @param record
     */
    @KafkaListener(topics = "topic_test", groupId = "group_topic_test")
    public void topicListener2(ConsumerRecord<String, String> record) {
        String value = record.value();
        log.info("topicListener2 value is :{}", value);
        log.info("topicListener2 record is :{}", record);
    }
    /**
     * kafka的监听器3，topic为"topic_test"，消费者组为"group_topic_test"
     *
     * @param record
     */
    @KafkaListener(topics = "topic_test", groupId = "group_topic_test")
    public void topicListener3(ConsumerRecord<String, String> record) {
        String value = record.value();
        log.info("topicListener3 value is :{}", value);
        log.info("topicListener3 record is :{}", record);
    }


    /**
     * 配置多个消费组
     * kafka的监听器2，topic为"topic_test2"，消费者组为"group_topic_test"
     *
     * @param record
     * @param item
     */
    @KafkaListener(topics = "topic_test2", groupId = "group_topic_test2")
    public void topicListener2(ConsumerRecord<String, String> record, Acknowledgment item) {
        String value = record.value();
        log.info("value is :{}", value);
        log.info("record is :{}", record);
        item.acknowledge();
    }


}
