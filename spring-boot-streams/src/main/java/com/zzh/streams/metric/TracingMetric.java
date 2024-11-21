package com.zzh.streams.metric;

import cn.hutool.json.JSONUtil;
import com.google.common.util.concurrent.AtomicDouble;
import com.zzh.streams.enums.TracingMetricEnum;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Tag;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/11/15 10:22
 */
@Slf4j
public class TracingMetric {


    public static void tracingRequestSumIncrement(Map<String, Object> tags) {
        Metrics.counter(TracingMetricEnum.TRACING_REQUEST_SUM.getCode(), tags.keySet().stream().map(o -> Tag.of(o, o)).collect(Collectors.toList())).increment();
    }


    private static final Map<String, AtomicLong> TRACING_REQUEST_GAUGE_SET = new ConcurrentHashMap<>();

    public static void tracingRequestSummaryMetric(Map<String, String> tags, Long num) {

        String generateMetricName = generateMetricName(TracingMetricEnum.TRACING_REQUEST_SUMMARY_METRIC.getCode(), tags);

        AtomicLong atomicLong = TRACING_REQUEST_GAUGE_SET.get(generateMetricName);
        if (Objects.nonNull(atomicLong)) {
            atomicLong.set(num);
            return;
        }
        AtomicLong target = new AtomicLong(num);
//        log.info("tars is :{}", JSONUtil.toJsonStr(tags));
        Metrics.gauge(TracingMetricEnum.TRACING_REQUEST_SUMMARY_METRIC.getCode(), tags.entrySet().stream().filter(entry -> Objects.nonNull(entry) && Objects.nonNull(entry.getKey()) && Objects.nonNull(entry.getValue())).map(entry -> Tag.of(entry.getKey(), entry.getValue())).collect(Collectors.toList()), target, AtomicLong::get);
        TRACING_REQUEST_GAUGE_SET.put(generateMetricName, target);

    }

    private static String generateMetricName(String metricName, Map<String, String> tags) {
        return metricName + tags.entrySet().stream().map(entry -> String.format("%s=%s", entry.getKey(), entry.getValue())).collect(Collectors.joining("&"));
    }


    private static final Map<String, AtomicDouble> TRACING_REQUEST_RATE_GAUGE_SET = new ConcurrentHashMap<>();

    public static void tracingRequestRateMetric(Map<String, String> tags, Double num) {

        String generateMetricName = generateMetricName(TracingMetricEnum.TRACING_REQUEST_RATE_METRIC.getCode(), tags);

        AtomicDouble rate = TRACING_REQUEST_RATE_GAUGE_SET.get(generateMetricName);
        if (Objects.nonNull(rate)) {
            rate.set(num);
            return;
        }
        AtomicDouble target = new AtomicDouble(num);
        Metrics.gauge(TracingMetricEnum.TRACING_REQUEST_RATE_METRIC.getCode(), tags.entrySet().stream().filter(entry -> Objects.nonNull(entry) && Objects.nonNull(entry.getKey()) && Objects.nonNull(entry.getValue())).map(entry -> Tag.of(entry.getKey(), entry.getValue())).collect(Collectors.toList()), target, AtomicDouble::get);
        TRACING_REQUEST_RATE_GAUGE_SET.put(generateMetricName, target);

    }


}
