package com.zzh.streams.config;

import cn.hutool.json.JSONUtil;
import com.zzh.streams.serde.Span;
import com.zzh.streams.enums.StreamsApplicationIDEnums;
import com.zzh.streams.serde.SpanListSerde;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.processor.WallclockTimestampExtractor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/11/14 19:50
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(StreamsProperties.class)
public class StreamsConfiguration {

    private static final String STREAMS_BUILDER_TRACE = "streamsBuilderTrace";

    @Resource
    private StreamsProperties streamsProperties;

    @Bean(name = STREAMS_BUILDER_TRACE)
    public StreamsBuilderFactoryBean defaultKafkaStreamsBuilder() {
        Map<String, Object> props = commonDefaultProperties();
        props.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG, streamsProperties.getNumThread());
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, StreamsApplicationIDEnums.TRACE_APPLICATION_ID.getCode());

//        props.put(StreamsConfig.consumerPrefix(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG), logKafkaStreamsProperties.getStreams().getConsumer().getAutoOffsetReset());
//        props.put(StreamsConfig.consumerPrefix(ConsumerConfig.MAX_POLL_RECORDS_CONFIG), logKafkaStreamsProperties.getStreams().getConsumer().getMaxPollRecords());
//
//        props.put(StreamsConfig.consumerPrefix(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG), Math.toIntExact(logKafkaStreamsProperties.getStreams().getConsumer().getAutoCommitInterval().toMillis()));
//        props.put(StreamsConfig.consumerPrefix(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG), Math.toIntExact(logKafkaStreamsProperties.getStreams().getConsumer().getFetchMaxWait().toMillis()));
//
//        props.put(StreamsConfig.consumerPrefix(ConsumerConfig.FETCH_MIN_BYTES_CONFIG), Math.toIntExact(logKafkaStreamsProperties.getStreams().getConsumer().getFetchMinSize().toBytes()));
//        props.put(StreamsConfig.consumerPrefix(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG), logKafkaStreamsProperties.getStreams().getConsumer().getEnableAutoCommit());

        return new StreamsBuilderFactoryBean(new KafkaStreamsConfiguration(props));
    }

    @Bean
    public Topology defaultTopology(@Qualifier(value = STREAMS_BUILDER_TRACE) StreamsBuilder streamsBuilder) {
        KStream<String, List<Span>> stream = streamsBuilder.stream(streamsProperties.getTopic(), Consumed.with(Serdes.String(), new SpanListSerde<>(Span.class)).withName("topic-input-processor"));
        stream.flatMapValues(value -> value).selectKey((key, value) -> value.getTraceId())
                .peek(((key, value) -> log.info("key is :{},value is :{}", key, JSONUtil.toJsonStr(value))));
        return streamsBuilder.build();
    }


    private Map<String, Object> commonDefaultProperties() {
        Map<String, Object> props = new HashMap<>();
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, streamsProperties.getBootstrapServers());
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, JsonSerde.class.getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, JsonSerde.class.getName());
        props.put(StreamsConfig.DEFAULT_TIMESTAMP_EXTRACTOR_CLASS_CONFIG, WallclockTimestampExtractor.class.getName());
        return props;
    }

}
