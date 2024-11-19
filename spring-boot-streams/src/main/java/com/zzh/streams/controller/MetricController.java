package com.zzh.streams.controller;

import com.zzh.streams.enums.TracingMetricEnum;
import com.zzh.streams.metric.TracingMetric;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Tags;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/11/19 15:28
 */
@Slf4j
@RestController
@RequestMapping("/metric")
public class MetricController {


    @RequestMapping()
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
}
