package com.zzh.springboot3.desin.pattern.singleton;

import lombok.Getter;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/9/9 17:10
 */
@Getter
public enum SingletonEnum {
    SINGLETON_ENUM("zzh", "zzh");

    private final String name;
    private final String pattern;

    SingletonEnum(String name, String pattern) {
        this.name = name;
        this.pattern = pattern;
    }
}
