package com.zzh.streams.controller;

import com.zzh.streams.enums.TracingMetricEnum;
import com.zzh.streams.metric.TracingMetric;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.Timer;
import io.prometheus.client.Histogram;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/11/19 15:28
 */
@Slf4j
@RestController
@RequestMapping("/metric")
public class MetricController {


    @RequestMapping("/summary")
    public void metrics() {
        DistributionSummary summary1 = Metrics.summary(TracingMetricEnum.TRACING_REQUEST_DURATION_SECONDS_METRIC.getName(), Tags.of("url", "/zzh"));
        DistributionSummary summary2 = Metrics.summary(TracingMetricEnum.TRACING_REQUEST_DURATION_SECONDS_METRIC.getName(), Tags.of("url", "/ylm"));
        DistributionSummary summary3 = Metrics.summary(TracingMetricEnum.TRACING_REQUEST_DURATION_SECONDS_METRIC.getName(), Tags.of("url", "/h"));
        DistributionSummary summary4 = Metrics.summary(TracingMetricEnum.TRACING_REQUEST_DURATION_SECONDS_METRIC.getName(), Tags.of("url", "/zzh"));
        DistributionSummary summary5 = Metrics.summary(TracingMetricEnum.TRACING_REQUEST_DURATION_SECONDS_METRIC.getName(), Tags.of("url", "/z"));
        DistributionSummary summary6 = Metrics.summary(TracingMetricEnum.TRACING_REQUEST_DURATION_SECONDS_METRIC.getName(), Tags.of("url", "/zh"));
        summary1.record(1000);
        summary2.record(2000);
        summary3.record(3000);
        summary4.record(1000);
        summary5.record(2000);
        summary6.record(3000);
    }

    @RequestMapping("historm")
    public void hist() {

        Histogram histogram = Histogram.build().name("histogram_test")
                .create();
        Histogram.Timer timer = histogram.startTimer();

        timer.observeDuration();

    }

    @RequestMapping("/gauge/{name}/{age}/{num}")
    public void gauge(@PathVariable String age, @PathVariable String name, @PathVariable Long num) {
        Map<String, String> tags = new HashMap<>();
        tags.put("name", name);
        tags.put("age", age);
        TracingMetric.tracingRequestSummaryMetric(tags,num);
    }
}
