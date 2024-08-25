package com.zzh.springboot3.prometheus;

import lombok.Data;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/8/22 17:43
 */
@Data
public class GaugeMetric {

    @ExcludeField
    private Long count;

    private String name;

    private String instance;

    private String application;

    public GaugeMetric(Long count, String name) {
        this.count = count;
        this.name = name;
        this.instance = "##";
        this.application = "##";
        GaugeMetrics.getInstance().register(this);
    }

    public GaugeMetric(Long count, String name, String instance, String application) {
        this.count = count;
        this.name = name;
        this.instance = instance;
        this.application = application;
        GaugeMetrics.getInstance().register(this);
    }
}
