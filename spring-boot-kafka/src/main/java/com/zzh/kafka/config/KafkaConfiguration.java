package com.zzh.kafka.config;

import com.zzh.springboot3.common.utils.JsonUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.context.event.GenericApplicationListener;
import org.springframework.core.ResolvableType;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.event.KafkaEvent;
import org.springframework.kafka.event.ListenerContainerIdleEvent;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.util.backoff.FixedBackOff;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2025/1/6 16:09
 */
@Slf4j
@Configuration
public class KafkaConfiguration {


    @Bean
    public ApplicationListener<ApplicationEvent> kafkaEventListener() {
        return new GenericApplicationListener() {
            @Override
            public boolean supportsEventType(@NonNull ResolvableType eventType) {
                return eventType.isAssignableFrom(KafkaEvent.class);
            }

            @Override
            public void onApplicationEvent(@NonNull ApplicationEvent event) {
                log.info("kafka event is :{}", JsonUtil.toJson(event));
            }
        };
    }

    @EventListener(classes = ApplicationEvent.class)
    public void onApplicationEvent(ApplicationEvent event) {
        log.info("application event is :{}", event.getSource());
    }

    @EventListener(condition = "event.listenerId.startsWith('qux-')")
    public void eventHandler(ListenerContainerIdleEvent event) {
        log.info("kafka event is :{}", event.getSource());
    }


    @Bean
    public ApplicationListener<ApplicationEvent> applicationListener(ObjectProvider<ApplicationContext> contextObjectProvider) {
        contextObjectProvider.ifAvailable((ctx) -> {
            log.info("applicationContext is :{}", ctx.toString());
        });

        return new GenericApplicationListener() {
            @Override
            public boolean supportsEventType(@NonNull ResolvableType eventType) {
                return eventType.isAssignableFrom(KafkaEvent.class);
            }

            @Override
            public void onApplicationEvent(@NonNull ApplicationEvent event) {
                log.info("kafka event is :{}", JsonUtil.toJson(event));
            }
        };
    }

//    public KafkaMessageListenerContainer<String, Object> KafkaMessageListenerContainer(DefaultKafkaConsumerFactory<?, ?> kafkaConsumerFactory) {
//
//        return new KafkaMessageListenerContainer<>(kafkaConsumerFactory,)
//    }

//    @Bean
//    public KafkaListenerContainerFactory<?> kafkaListenerContainerFactory(ConsumerFactory<String, String> consumerFactory) {
//        ConcurrentKafkaListenerContainerFactory factory = new ConcurrentKafkaListenerContainerFactory();
//        // 自定义重试时间间隔以及次数
//        FixedBackOff fixedBackOff = new FixedBackOff(1000, 5);
//        factory.setCommonErrorHandler(new DefaultErrorHandler(fixedBackOff));
//        factory.setConsumerFactory(consumerFactory);
//        return factory;
//    }
}
