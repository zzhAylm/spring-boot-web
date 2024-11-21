package com.zzh.streams.enums;

import lombok.Getter;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/11/15 10:34
 */
@Getter
public enum TracingMetricEnum {
    TRACING_REQUEST_SUM("tracing_request_sum", "请求总量"),
    TRACING_REQUEST_ERROR_SUM("tracing_request_error_sum", "请求失败总量"),
    TRACING_REQUEST_CONSUMER_SUM("tracing_request_consumer_sum", "消费者请求总量"),
    TRACING_REQUEST_DURATION_SECONDS_METRIC("tracing_request_duration_seconds_metric", "耗时，应用，实例，接口"),
    TRACING_REQUEST_SUMMARY_METRIC("tracing_request_summary_metric", "耗时，应用，实例，接口"),
    TRACING_REQUEST_RATE_METRIC("tracing_request_rate_metric", "接口的实时速率，label：应用，实例，接口");

    private final String code;
    private final String name;

    TracingMetricEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
