package com.zzh.streams.config;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.json.JSONUtil;
import com.zzh.streams.domain.TracingRequestSumKey;
import com.zzh.streams.metric.TracingMetric;
import com.zzh.streams.processor.TracingRequestSumProcessor;
import com.zzh.streams.serde.*;
import com.zzh.streams.enums.ApplicationIdEnum;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.processor.WallclockTimestampExtractor;
import org.apache.kafka.streams.processor.api.Processor;
import org.apache.kafka.streams.processor.api.ProcessorSupplier;
import org.apache.kafka.streams.processor.api.Record;
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
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static com.zzh.streams.constant.TracingConstant.*;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/11/14 19:50
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(StreamsProperties.class)
public class StreamsConfiguration {


    @Resource
    private StreamsProperties streamsProperties;

    @Bean(name = TRACING_STREAMS_BUILDER)
    public StreamsBuilderFactoryBean defaultKafkaStreamsBuilder() {
        Map<String, Object> props = commonDefaultProperties();
        props.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG, streamsProperties.getNumThread());
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, ApplicationIdEnum.TRACE_APPLICATION_ID.getCode());

        return new StreamsBuilderFactoryBean(new KafkaStreamsConfiguration(props));
    }

    @Bean
    public Topology defaultTopology(@Qualifier(value = TRACING_STREAMS_BUILDER) StreamsBuilder streamsBuilder) {
        KStream<String, List<Span>> stream = streamsBuilder.stream(streamsProperties.getTopic(), Consumed.with(Serdes.String(), new SpanListSerde<>(Span.class)).withName("tracing-span-topic-input-processor"));

        KStream<String, Span> spanKStream = stream.flatMapValues((value -> value), Named.as("tracing_span_flat_map_values"));

        // 根据tracingId重分区
        spanKStream.map((key, value) -> KeyValue.pair(value.getTraceId(), value), Named.as("tracing_span_map_value_key_trace_id"))
                .to(TRACING_SPAN_GROUP_BY_TRACING_ID_TOPIC, Produced.with(Serdes.String(), new JsonSerde<>(Span.class)));

//        KStream<String, Span> spanKStream = stream.flatMap((key, value) -> value.stream().map(span -> KeyValue.pair(span.getTraceId(), span)).collect(Collectors.toList()))
//                .peek(((key, value) -> log.info("key is :{},value is :{}", key, JSONUtil.toJsonStr(value))))
//                ;

//        KStream<String, Span> spanKStream = stream.flatMapValues(value -> value).selectKey((key, value) -> value.getTraceId())
//                .peek(((key, value) -> log.info("key is :{},value is :{}", key, JSONUtil.toJsonStr(value))))
        ;
//        spanKStream.groupByKey(Grouped.with(Serdes.String(),new SpanSerde())).reduce((value1, value2) -> value1).toStream().foreach((key, value) -> {
//            TracingMetric.tracingRequestSumIncrement(new HashMap<>());
//        });

        // 根据 request summary 重分区
        spanKStream.map((key, value) -> KeyValue.pair(generateMetricName(value), value.getDuration()), Named.as("tracing_request_summary_map"))
                .to(TRACING_SPAN_GROUP_BY_SUMMARY_TOPIC, Produced.with(new JsonSerde<>(TracingSummaryMetric.class), Serdes.Long()).withName("tracing_request_summary_map_to"));

        return streamsBuilder.build();
    }


    @Bean(name = TRACING_REQUEST_SUM_STREAMS_BUILDER)
    public StreamsBuilderFactoryBean TraceRequestSumStreamsBuilder() {
        Map<String, Object> props = commonDefaultProperties();
        props.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG, streamsProperties.getNumThread());
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, ApplicationIdEnum.TRACE_REQUEST_SUM_APPLICATION_ID.getCode());
        return new StreamsBuilderFactoryBean(new KafkaStreamsConfiguration(props));
    }

    @Bean
    public Topology tracingRequestSumTopology(@Qualifier(value = TRACING_REQUEST_SUM_STREAMS_BUILDER) StreamsBuilder streamsBuilder) {

        KStream<String, Span> spanKStream = streamsBuilder.stream(TRACING_SPAN_GROUP_BY_TRACING_ID_TOPIC, Consumed.with(Serdes.String(), new JsonSerde<>(Span.class)).withName("tracing-span-request-sum-repartition-topic-input-processor"));

        spanKStream.process(new ProcessorSupplier<String, Span, String, Long>() {
            @Override
            public Set<StoreBuilder<?>> stores() {
                StoreBuilder<WindowStore<String, Long>> windowStoreStoreBuilder = Stores.windowStoreBuilder(Stores.inMemoryWindowStore(TRACING_REQUEST_SUM_WINDOW_STORE, Duration.ofMinutes(2), Duration.ofMinutes(1), false), Serdes.String(), Serdes.Long()).withLoggingDisabled();
                StoreBuilder<KeyValueStore<TracingRequestSumKey, Long>> keyValueStoreStoreBuilder = Stores.keyValueStoreBuilder(Stores.inMemoryKeyValueStore(TRACING_REQUEST_SUM_STORE), new TracingRequstSumKeySerde(), Serdes.Long());
                Set<StoreBuilder<?>> storeBuilders = new HashSet<>();
                storeBuilders.add(windowStoreStoreBuilder);
                storeBuilders.add(keyValueStoreStoreBuilder);
                return storeBuilders;
            }

            @Override
            public Processor<String, Span, String, Long> get() {
                return new TracingRequestSumProcessor();
            }
        });

        return streamsBuilder.build();
    }


    @Bean(name = TRACING_REQUEST_SUMMARY_STREAMS_BUILDER)
    public StreamsBuilderFactoryBean tracingRequestSummaryStreamsBuilder() {
        Map<String, Object> props = commonDefaultProperties();
        props.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG, streamsProperties.getNumThread());
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, ApplicationIdEnum.TRACE_REQUEST_SUMMARY_APPLICATION_ID.getCode());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, CustomJsonDeserializer.class);
        return new StreamsBuilderFactoryBean(new KafkaStreamsConfiguration(props));
    }


    @Bean
    public Topology tracingRequestTopology(@Qualifier(value = TRACING_REQUEST_SUMMARY_STREAMS_BUILDER) StreamsBuilder streamsBuilder) {
        KStream<TracingSummaryMetric, Long> summarySteam = streamsBuilder.stream(TRACING_SPAN_GROUP_BY_SUMMARY_TOPIC, Consumed.with(new JsonSerde<>(TracingSummaryMetric.class), Serdes.Long()).withName("tracing-span-by-summary-repartition-topic-input-processor"));

        // 自定义状态存储配置，关闭变更日志记录
        Materialized<TracingSummaryMetric, Long, WindowStore<Bytes, byte[]>> materialized = Materialized.<TracingSummaryMetric, Long, WindowStore<org.apache.kafka.common.utils.Bytes, byte[]>>as("tracingRequestSummaryAggregate").withKeySerde(new JsonSerde<>(TracingSummaryMetric.class)).withValueSerde(Serdes.Long()).withLoggingDisabled(); // 关闭变更日志记录

        summarySteam
                .groupByKey(Grouped.as("tracingRequestGroupByKey"))
                .windowedBy(TimeWindows.ofSizeAndGrace(Duration.ofMinutes(1), Duration.ofSeconds(30))).emitStrategy(EmitStrategy.onWindowClose())
                .aggregate(() -> Long.valueOf(0), (key, value, aggregate) -> {
//                    log.info("key is {},value is :{}", JSONUtil.toJsonStr(key), value);
                    TracingMetric.tracingRequestSummaryMetric(tags(key), value);
                    return aggregate;
                }, Named.as("tracing_request_summary_aggregate"), materialized)
        ;

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


    public TracingSummaryMetric generateMetricName(Span span) {
        TracingSummaryMetric tracMetric = new TracingSummaryMetric();
        tracMetric.setTracingName(StringUtils.defaultIfBlank(span.getName(), "default"));
        if (Objects.nonNull(span.getLocalEndpoint())) {
            tracMetric.setPort(span.getLocalEndpoint().getPort());
            tracMetric.setServiceName(span.getLocalEndpoint().getServiceName());
            tracMetric.setPort(span.getLocalEndpoint().getPort());
        }
        if (Objects.nonNull(span.getRemoteEndpoint())) {
            tracMetric.setRemoteEndpointServiceName(span.getRemoteEndpoint().getServiceName());
            tracMetric.setRemoteEndpointPort(span.getRemoteEndpoint().getPort());
            tracMetric.setRemoteEndpointInstance(span.getRemoteEndpoint().getIpv4());
        }
        tracMetric.setKind(span.getKind());
        if (Objects.nonNull(span.getTags())) {
            tracMetric.setMethod(span.getTags().get("http.method"));
            tracMetric.setUrl(span.getTags().get("http.path"));
        }
        return tracMetric;
    }

    public static <T> Map<String, String> tags(T obj) {
        Map<String, String> tags = new HashMap<>();
        Arrays.stream(ReflectUtil.getFields(obj.getClass())).forEach(field -> {
            String value = "EMPTY";
            try {
                field.setAccessible(true);
                if (field.getType().equals(Integer.class)) {
                    value = String.valueOf(field.get(obj));
                } else {
                    value = (String) field.get(obj);
                }
            } catch (IllegalAccessException e) {
                log.error("获取字段值失败，使用默认值EMPTY", e);
            }
            tags.put(field.getName(), value);
        });
        return tags;
    }
}
