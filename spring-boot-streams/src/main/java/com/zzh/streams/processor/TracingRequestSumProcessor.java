package com.zzh.streams.processor;


import cn.hutool.json.JSONUtil;
import com.zzh.streams.domain.TracingRequestSumKey;
import com.zzh.streams.metric.TracingMetric;
import com.zzh.streams.serde.Span;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Windowed;
import org.apache.kafka.streams.processor.PunctuationType;
import org.apache.kafka.streams.processor.StateStore;
import org.apache.kafka.streams.processor.api.*;
import org.apache.kafka.streams.processor.api.Record;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.WindowStore;

import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/11/15 14:17
 */
@Slf4j
public class TracingRequestSumProcessor extends ContextualProcessor<String, Span, TracingRequestSumKey, Long> {

    public static final String TRACING_REQUEST_SUM_STORE = "tracing_request_sum_store";
    public static final String TRACING_REQUEST_SUM_WINDOW_STORE = "tracing_request_sum_window_store";

    // 需要的指标 直接按key value 查询，永久存储，显示删除
    private KeyValueStore<TracingRequestSumKey, Long> stateStore;

    //  需要的指标按时间窗口查询，维护窗口大小，过期自动删除
    private WindowStore<TracingRequestSumKey, Long> windowStore;

    @Override
    public void init(ProcessorContext<TracingRequestSumKey, Long> context) {
        super.init(context);
        this.stateStore = context().getStateStore(TRACING_REQUEST_SUM_STORE);
        this.windowStore = context().getStateStore(TRACING_REQUEST_SUM_WINDOW_STORE);
        context().schedule(Duration.ofSeconds(1), PunctuationType.WALL_CLOCK_TIME, (timestamp -> {
            log.info("窗口定时任务执行，time is :{}", timestamp);
            long timeTo = timestamp - timestamp % 1000;
            KeyValueIterator<Windowed<TracingRequestSumKey>, Long> windowedLongKeyValueIterator = windowStore.fetchAll(timeTo - 1000, timeTo);
            while (windowedLongKeyValueIterator.hasNext()) {
                KeyValue<Windowed<TracingRequestSumKey>, Long> next = windowedLongKeyValueIterator.next();
                TracingMetric.tracingRequestSumIncrement(new HashMap<>());
                log.info("窗口store key is :{},value is :{}", next.key.key(), next.value);
            }
        }));
    }

    @Override
    public void process(Record<String, Span> record) {
        if (record.key() == null) {
            return;
        }
        int partitionId = 0;
        if (context().recordMetadata().isPresent()) {
            RecordMetadata recordMetadata = context().recordMetadata().get();
            partitionId = recordMetadata.partition();
        }
        TracingRequestSumKey tracingRequestSumKey = new TracingRequestSumKey();
        tracingRequestSumKey.setTraceId(record.key());
        tracingRequestSumKey.setPartitionId(partitionId);
        Long fetch = windowStore.fetch(tracingRequestSumKey, record.timestamp() - record.timestamp() % (1000 * 60));
        if (fetch == null) {
            fetch = 0L;
        }
        fetch++;
        windowStore.put(tracingRequestSumKey, fetch, record.timestamp());

//        Long count = stateStore.get(tracingRequestSumKey);
//        if (count == null) {
//            stateStore.put(tracingRequestSumKey, 1L);
//            log.info("record  key is :{},record value is :{}", record.key(), record.value());
//            TracingMetric.tracingRequestSumIncrement(new HashMap<>());
//        } else {
//            stateStore.put(tracingRequestSumKey, ++count);
//        }

    }

}
