package com.zzh.springboot.rabbit.config;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

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

    // InvocationHandler：  @RabbitListener 标注的bean 和 method 创建一个InvocationHandler
    // 最后消费消息的都是 MessageListenerContainer ，MessageListenerContainer都是通过MessageListener  onMessage 来消费消息的
    // @RabbitListener 的的方式就是扫描 bean 和 method ，创建一个InvocationHandler ，然后通过MessageListenerContainer 来消费消息的，MessageListener 是一个
    // 固定的MessageListenerAdaptor ，在onmessage 中调用 InvocationHandler 的 invoke 方法，注入参数，来实现消息的处理
    @RabbitListener(queues = "topic.zzh_queue_routing_keys")
    public void messageListenerTopicRoutingKeys(Message message, Channel channel) throws IOException {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            // 获取消息体
            String msg = new String(message.getBody(), "UTF-8");
            System.out.println("Received: " + msg);

            // 模拟业务处理
            processMessage(msg);

            // 手动确认消息
            channel.basicAck(deliveryTag, false);
            System.out.println("Message acknowledged");

        } catch (Exception e) {
            // 处理异常时拒绝消息，并重新入队
            channel.basicNack(deliveryTag, false, true);
            System.out.println("Message requeued due to error: " + e.getMessage());
        }
    }

    private void processMessage(String msg) throws Exception {
        // 模拟异常
        if (msg.contains("error")) {
            throw new Exception("Processing failed for message: " + msg);
        }
        System.out.println("Processed message: " + msg);
    }


    @RabbitListener(queues = "topic.zzh_queue_routing_keys")
    public void messageListenerTopicRoutingKeys(String message) {
        log.info("rabbit topic routing keys consumer message is : {}", message);
    }
}
