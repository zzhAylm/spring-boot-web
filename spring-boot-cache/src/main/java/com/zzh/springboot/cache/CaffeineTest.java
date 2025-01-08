package com.zzh.springboot.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/11/4 17:17
 */
@Slf4j
public class CaffeineTest {


    public void caffeineTest(){
        Cache<Object, Object> build = Caffeine.newBuilder()
                .initialCapacity(100)
                .build();
    }



}
