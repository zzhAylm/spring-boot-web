package com.zzh.streams.config;

import cn.hutool.json.JSONUtil;
import com.zzh.streams.domain.TracingRequestSumKey;
import com.zzh.streams.metric.TracingMetric;
import com.zzh.streams.processor.TracingRequestSumProcessor;
import com.zzh.streams.serde.Span;
import com.zzh.streams.enums.ApplicationIdEnum;
import com.zzh.streams.serde.SpanListSerde;
import com.zzh.streams.serde.SpanSerde;
import com.zzh.streams.serde.TracingRequstSumKeySerde;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.errors.DeserializationExceptionHandler;
import org.apache.kafka.streams.errors.LogAndContinueExceptionHandler;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Named;
import org.apache.kafka.streams.processor.WallclockTimestampExtractor;
import org.apache.kafka.streams.processor.api.Processor;
import org.apache.kafka.streams.processor.api.ProcessorSupplier;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.StoreBuilder;
import org.apache.kafka.streams.state.Stores;
import org.apache.kafka.streams.state.WindowStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

import static com.zzh.streams.processor.TracingRequestSumProcessor.TRACING_REQUEST_SUM_STORE;
import static com.zzh.streams.processor.TracingRequestSumProcessor.TRACING_REQUEST_SUM_WINDOW_STORE;

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
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, ApplicationIdEnum.TRACE_APPLICATION_ID.getCode());

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

        KStream<String, Span> spanKStream = stream.flatMap((key, value) -> value.stream().map(span -> KeyValue.pair(span.getTraceId(), span)).collect(Collectors.toList()))
                .peek(((key, value) -> log.info("key is :{},value is :{}", key, JSONUtil.toJsonStr(value))))
                ;

//        KStream<String, Span> spanKStream = stream.flatMapValues(value -> value).selectKey((key, value) -> value.getTraceId())
//                .peek(((key, value) -> log.info("key is :{},value is :{}", key, JSONUtil.toJsonStr(value))))
                ;
//        spanKStream.groupByKey(Grouped.with(Serdes.String(),new SpanSerde())).reduce((value1, value2) -> value1).toStream().foreach((key, value) -> {
//            TracingMetric.tracingRequestSumIncrement(new HashMap<>());
//        });

        spanKStream.process(new ProcessorSupplier<String, Span, TracingRequestSumKey, Long>() {
            @Override
            public Set<StoreBuilder<?>> stores() {
                StoreBuilder<WindowStore<TracingRequestSumKey, Long>> windowStoreStoreBuilder = Stores.windowStoreBuilder(Stores.inMemoryWindowStore(TRACING_REQUEST_SUM_WINDOW_STORE, Duration.ofMinutes(2), Duration.ofMinutes(1), false), new JsonSerde<>(TracingRequestSumKey.class), Serdes.Long()).withLoggingDisabled();
                StoreBuilder<KeyValueStore<TracingRequestSumKey, Long>> keyValueStoreStoreBuilder = Stores.keyValueStoreBuilder(Stores.inMemoryKeyValueStore(TRACING_REQUEST_SUM_STORE), new TracingRequstSumKeySerde(), Serdes.Long());
                Set<StoreBuilder<?>> storeBuilders = new HashSet<>();
                storeBuilders.add(windowStoreStoreBuilder);
                storeBuilders.add(keyValueStoreStoreBuilder);
                return storeBuilders;
            }

            @Override
            public Processor<String, Span, TracingRequestSumKey, Long> get() {
                return new TracingRequestSumProcessor();
            }
        });


        KStream<String, Span> spanKStream1 = stream.flatMap(((key, value) -> value.stream().map(span -> {
            return KeyValue.pair(span.getTraceId(), span);
        }).collect(Collectors.toList())), Named.as("tracing_request_duration_seconds_flat_map"));


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
