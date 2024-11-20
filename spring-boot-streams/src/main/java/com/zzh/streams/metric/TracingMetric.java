package com.zzh.streams.metric;

import com.zzh.streams.enums.TracingMetricEnum;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Tag;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
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





    private static final Map<String, TracingRequestGauge> TRACING_REQUEST_GAUGE_SET = new ConcurrentHashMap<>();

    public static void tracingRequestSummaryMetric(Map<String, String> tags, Long num) {

        String generateMetricName = generateMetricName(TracingMetricEnum.TRACING_REQUEST_DURATION_SECONDS_METRIC.getName(), tags);

        TracingRequestGauge gauge = TRACING_REQUEST_GAUGE_SET.get(generateMetricName);
        if (Objects.nonNull(gauge)) {
            gauge.setCount(num);
            return;
        }
        TracingRequestGauge tracingRequestGauge = new TracingRequestGauge();
        tracingRequestGauge.setTags(tags);
        tracingRequestGauge.setCount(num);
        Metrics.gauge(TracingMetricEnum.TRACING_REQUEST_DURATION_SECONDS_METRIC.getName(), tags.keySet().stream().map(o -> Tag.of(o, o)).collect(Collectors.toList()), tracingRequestGauge, TracingRequestGauge::getCount);
        TRACING_REQUEST_GAUGE_SET.put(generateMetricName, tracingRequestGauge);

    }

    public static String generateMetricName(String metricName, Map<String, String> tags) {
        return metricName + tags.entrySet().stream().map(entry -> String.format("%s=%s", entry.getKey(), entry.getValue())).collect(Collectors.joining(","));
    }

    @Data
    public static class TracingRequestGauge {
        private Map<String, String> tags;
        private Long count;

    }


}
