package com.zzh.springboot3.pattern;

import org.apache.poi.ss.formula.functions.T;

import java.util.Optional;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/6/12 15:11
 */
public class OptionalMain {
    public static void main(String[] args) {
        Optional<String> zzh = Optional.of("zzh");

        Optional<Integer> t = Optional.of(67);
    }
}
