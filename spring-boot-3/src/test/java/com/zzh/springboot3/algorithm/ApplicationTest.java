package com.zzh.springboot3.algorithm;

import com.zzh.springboot3.service.RedissonService;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class ApplicationTest {

    @Resource
    private RedissonService redissonService;

    @Test
    void test() {
        long startTime = System.currentTimeMillis();
        log.info("application test start is {}", startTime);

        redissonService.getLock();


        long endTime = System.currentTimeMillis();
        log.info("application test end time is {},execution time: {} milliseconds", endTime, endTime - startTime);
    }

    private static Map<Meter.Id, String> map = new ConcurrentHashMap<>();

    @Test
    public void testMeterId() {
        Tags tags = Tags.of(Tag.of("name", "zzh"));
        Meter.Id zzh = new Meter.Id("zzh", tags, null, null, Meter.Type.GAUGE);
        map.put(zzh, "string");
        Meter.Id zzh2 = new Meter.Id("zzh", Tags.of(Tag.of("name", "zzh")), null, null, Meter.Type.GAUGE);
        log.info("zzh2是否存在,{}", map.containsKey(zzh2));
        Meter.Id zzh3 = new Meter.Id("zzh", Tags.of(Tag.of("name", "zzh1")), null, null, Meter.Type.GAUGE);
        log.info("zzh3是否存在,{}", map.containsKey(zzh3));
        map.put(zzh3,"zzh3");
        Meter.Id zzh4 = new Meter.Id("zzh", Tags.of(Tag.of("name", "zzh1")), null, null, Meter.Type.GAUGE);
        log.info("zzh4是否存在,{}", map.containsKey(zzh4));
    }


}
