package com.zzh.streams.enums;

import lombok.Getter;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/11/15 11:25
 */
@Getter
public enum MetricTagEnum {
    SERVICE("service", "服务调用"),
    URL("url", "接口调用");

    private final String code;
    private final String name;

    MetricTagEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
