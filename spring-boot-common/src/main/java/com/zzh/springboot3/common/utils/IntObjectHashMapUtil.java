package com.zzh.springboot3.common.utils;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/12/31 13:47
 */
public class IntObjectHashMapUtil {

    public static void main(String[] args) {

        Int2ObjectOpenHashMap<String> objectOpenHashMap = new Int2ObjectOpenHashMap<>();
        objectOpenHashMap.put(1, "1");
        objectOpenHashMap.put(2, "2");
        objectOpenHashMap.forEach((k, v) -> {
            System.out.println(k + ":" + v);
        });




    }
}
