package com.zzh.springboot3.prometheus;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Timer;
import io.prometheus.client.Histogram;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * @Description:
 * @Author: zzh
 * @Create 2025/3/19 16:27
 */
@Component
public class HistogramMetric {

    private final Timer requestTimer;

    public HistogramMetric(MeterRegistry registry) {
        this.requestTimer = Timer.builder("http_request_duration_seconds")
                .description("HTTP 请求耗时")
                .publishPercentiles(0.5, 0.95, 0.99) // 计算 P50, P95, P99
                .publishPercentileHistogram()
                .register(registry);
    }
    public void recordRequest(long durationMs) {
        requestTimer.record(Duration.ofMillis(durationMs));
    }



}
