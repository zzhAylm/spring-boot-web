package com.zzh.streams.enums;

import lombok.Getter;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/11/14 20:38
 */
@Getter
public enum StreamsApplicationIDEnums {

    TRACE_APPLICATION_ID("trace", "调用链");

    StreamsApplicationIDEnums(String code, String name) {
        this.code = code;
        this.name = name;
    }
    private final String code;
    private final String name;
}
