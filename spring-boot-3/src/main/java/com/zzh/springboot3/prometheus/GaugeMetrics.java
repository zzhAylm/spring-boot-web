package com.zzh.springboot3.prometheus;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.json.JSONUtil;
import io.micrometer.core.instrument.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/8/22 17:45
 */
@Data
@Slf4j
public class GaugeMetrics implements Serializable {

    private static final String JOB_NAME = "gauge-job";

    private GaugeMetrics() {
    }

    private static Map<String, GaugeMetric> metricMap = new ConcurrentHashMap<>();

    public void register(GaugeMetric newMetric) {
        GaugeMetric curMetric = metricMap.computeIfAbsent(generate(newMetric), key -> {
            Gauge.builder(newMetric.getName(), newMetric, GaugeMetric::getCount)
                    .tag("job", JOB_NAME)
                    .tag("instance", newMetric.getInstance())
                    .tag("application", newMetric.getApplication())
                    .register(Metrics.globalRegistry);
            log.info("注册指标:{}", JSONUtil.toJsonStr(newMetric));
            return newMetric;
        });
        curMetric.setCount(newMetric.getCount());
    }

    public static GaugeMetrics getInstance() {
        return GaugeMetrics.GaugeMetricsHolder.instance;
    }

    private static class GaugeMetricsHolder {
        private static final GaugeMetrics instance = new GaugeMetrics();
    }


    public String generate(Object newMetric) {
        return Arrays.stream(ReflectUtil.getFields(newMetric.getClass(), (field) -> !field.isAnnotationPresent(ExcludeField.class)))
                .map(field -> {
                    field.setAccessible(true);
                    try {
                        return (String) field.get(newMetric);
                    } catch (IllegalAccessException e) {
                        log.error("", e);
                        return "";
                    }
                }).reduce((str1, str2) -> str1 + str2).orElseGet(newMetric::toString);
    }

}
