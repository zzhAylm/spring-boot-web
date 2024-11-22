package com.zzh.streams.metric;

import com.google.common.util.concurrent.AtomicDouble;
import com.zzh.streams.enums.TracingMetricEnum;
import com.zzh.streams.serde.TracingRequestRateMetric;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Tag;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static com.zzh.streams.constant.TracingConstant.TRACING_REQUEST_RATE_WINDOW_SIZE;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/11/15 10:22
 */
@Slf4j
@Component
public class TracingMetric {

    private static final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);


    private static final Map<String, TracingRequestSummaryGauge> TRACING_REQUEST_GAUGE_SET = new ConcurrentHashMap<>();

    private static final Map<String, TracingRequestRateGauge> TRACING_REQUEST_RATE_GAUGE_SET = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        this.scheduleUpdateTracingRequestRateMetric();
        this.scheduleUpdateTracingRequestSummaryMetric();
    }

    public void scheduleUpdateTracingRequestRateMetric() {
        scheduledExecutorService.scheduleAtFixedRate(() -> {
                    log.info("开始执行定时任务，scheduleUpdateTracingRequestRateMetric");
                    TRACING_REQUEST_RATE_GAUGE_SET.forEach((k, v) -> v.valueSet0());
                }
                , 30000, TRACING_REQUEST_RATE_WINDOW_SIZE, TimeUnit.MILLISECONDS);
    }

    public void scheduleUpdateTracingRequestSummaryMetric() {
        scheduledExecutorService.scheduleAtFixedRate(() -> {
                    log.info("开始执行定时任务，scheduleUpdateTracingRequestSummaryMetric");
                    TRACING_REQUEST_RATE_GAUGE_SET.forEach((k, v) -> v.valueSet0());
                }
                , 30000, TRACING_REQUEST_RATE_WINDOW_SIZE, TimeUnit.MILLISECONDS);
    }


    public static void tracingRequestSumIncrement(Map<String, Object> tags) {
        Metrics.counter(TracingMetricEnum.TRACING_REQUEST_SUM.getCode(), tags.keySet().stream().map(o -> Tag.of(o, o)).collect(Collectors.toList())).increment();
    }


    public static void tracingRequestSummaryMetric(Map<String, String> tags, Long num) {

        String generateMetricName = generateMetricName(TracingMetricEnum.TRACING_REQUEST_SUMMARY_METRIC.getCode(), tags);

        TracingRequestSummaryGauge requestSummaryGauge = TRACING_REQUEST_GAUGE_SET.get(generateMetricName);
        if (Objects.nonNull(requestSummaryGauge)) {
            requestSummaryGauge.getValue().set(num);
            requestSummaryGauge.setLastUpdateTime(System.currentTimeMillis());
            return;
        }
        TracingRequestSummaryGauge gauge = new TracingRequestSummaryGauge();
        gauge.setValue(new AtomicLong(num));
//        log.info("tars is :{}", JSONUtil.toJsonStr(tags));
        Metrics.gauge(TracingMetricEnum.TRACING_REQUEST_SUMMARY_METRIC.getCode(), tags.entrySet().stream().filter(entry -> Objects.nonNull(entry) && Objects.nonNull(entry.getKey()) && Objects.nonNull(entry.getValue())).map(entry -> Tag.of(entry.getKey(), entry.getValue())).collect(Collectors.toList()), gauge, (obj) -> obj.getValue().get());
        TRACING_REQUEST_GAUGE_SET.put(generateMetricName, gauge);
    }


    private static String generateMetricName(String metricName, Map<String, String> tags) {
        return metricName + tags.entrySet().stream().map(entry -> String.format("%s=%s", entry.getKey(), entry.getValue())).collect(Collectors.joining("&"));
    }


    public static void tracingRequestRateMetric(Map<String, String> tags, Double num) {

        String generateMetricName = generateMetricName(TracingMetricEnum.TRACING_REQUEST_RATE_METRIC.getCode(), tags);

        TracingRequestRateGauge rate = TRACING_REQUEST_RATE_GAUGE_SET.get(generateMetricName);
        if (Objects.nonNull(rate)) {
            rate.getValue().set(num);
            rate.setLastUpdateTime(System.currentTimeMillis());
            return;
        }
        TracingRequestRateGauge gauge = new TracingRequestRateGauge();
        gauge.setValue(new AtomicDouble(num));
        Metrics.gauge(TracingMetricEnum.TRACING_REQUEST_RATE_METRIC.getCode(), tags.entrySet().stream().filter(entry -> Objects.nonNull(entry) && Objects.nonNull(entry.getKey()) && Objects.nonNull(entry.getValue())).map(entry -> Tag.of(entry.getKey(), entry.getValue())).collect(Collectors.toList()), gauge, (obj) -> obj.getValue().get());
        TRACING_REQUEST_RATE_GAUGE_SET.put(generateMetricName, gauge);
    }


    @Data
    private static class TracingRequestRateGauge implements Serializable {

        private AtomicDouble value;

        private Long lastUpdateTime;


        public void setValue(AtomicDouble value) {
            this.value = value;
            this.lastUpdateTime = System.currentTimeMillis();
        }


        public void valueSet0() {
            if (System.currentTimeMillis() - lastUpdateTime > TRACING_REQUEST_RATE_WINDOW_SIZE) {
                value.set(0);
            }
        }
    }

    @Data
    public static class TracingRequestSummaryGauge implements Serializable {
        private AtomicLong value;
        private Long lastUpdateTime;

        public void setValue(AtomicLong value) {
            this.value = value;
            this.lastUpdateTime = System.currentTimeMillis();
        }

        public void valueSet0() {
            if (System.currentTimeMillis() - lastUpdateTime > TRACING_REQUEST_RATE_WINDOW_SIZE) {
                value.set(0);
            }
        }

    }


}
