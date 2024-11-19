package com.zzh.streams.enums;

import lombok.Getter;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/11/14 20:38
 */
@Getter
public enum ApplicationIdEnum {

    TRACE_APPLICATION_ID("trace_zipkin_application_id", "调用链");

    ApplicationIdEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
    private final String code;
    private final String name;
}
