package com.zzh.springboot3.controller;

import cn.hutool.core.util.RandomUtil;
import com.zzh.springboot3.prometheus.GaugeMetric;
import com.zzh.springboot3.prometheus.HistogramMetric;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.Metrics;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/8/16 17:20
 */
@RestController
@RequestMapping("/prometheus")
public class PrometheusController {

    private static final Map<String, AtomicInteger> cache = new ConcurrentHashMap<>();


    @GetMapping("/gauge/{ip}")
    public void count(@PathVariable String ip) {
        AtomicInteger atomicInteger = cache.get(ip);
        if (atomicInteger == null) {
            AtomicInteger newInteger = new AtomicInteger(0);
            Gauge.builder("guava_metric", () -> newInteger).tag("IP", ip)
                    .register(Metrics.globalRegistry);
            cache.put(ip, newInteger);
        } else {
            atomicInteger.incrementAndGet();
        }
    }


    @GetMapping("/gauge/{count}/{name}")
    public GaugeMetric gauge(@PathVariable Long count, @PathVariable String name) {
        return new GaugeMetric(count, name);
    }

    @GetMapping("/gauge/{count}/{name}/{application}")
    public GaugeMetric gaugeTags(@PathVariable Long count, @PathVariable String name, @PathVariable String application) {
        return new GaugeMetric(count, name, "172.16.46.134",application);
    }

    @Resource
    private HistogramMetric histogramMetric;

    @GetMapping("/histogram")
    public void histogram() {
        histogramMetric.recordRequest(RandomUtil.randomInt(1000));
    }

}
