package com.zzh.streams.enums;

import lombok.Getter;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/11/14 20:38
 */
@Getter
public enum ApplicationIdEnum {

    TRACE_APPLICATION_ID("trace_span_zipkin_1_application_id", "调用链"),
    TRACE_REQUEST_SUM_APPLICATION_ID("trace_zipkin_request_sum_application_id", "请求总量"),
    TRACE_REQUEST_SUMMARY_APPLICATION_ID("trace_span_zipkin_request_summary_application_id", "调用链汇总"),
    ;

    ApplicationIdEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
    private final String code;
    private final String name;
}
