package com.zzh.springboot;

import com.zzh.springboot.cache.EnableDoubleCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description:
 * @Author: zzh
 * @Crete ${DATE} ${TIME}
 */
@EnableDoubleCache
@SpringBootApplication
public class CacheApplication {
    public static void main(String[] args) {
        SpringApplication.run(CacheApplication.class, args);
    }
}
