package com.zzh.springboot3;

import com.zzh.springboot3.cache.DoubleCacheMethodInterceptor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;


@Slf4j
public class ApplicationSpringParaserTest {


    @Test
    public void test() {
        long startTime = System.currentTimeMillis();
        log.info("application test start is {}", startTime);

        String elStr = "#id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", "1");
        Order order = new Order();
        order.setId("1024");
        order.setName("zzh");
        params.put("order", order);

        String parser = DoubleCacheMethodInterceptor.parser(elStr, params);
        log.info("id is :{}", parser);

        String orderId = DoubleCacheMethodInterceptor.parser("#order.id", params);
        String orderName = DoubleCacheMethodInterceptor.parser("#order.name", params);
        log.info("order id is :{},order name is :{}", orderId,orderName);

        long endTime = System.currentTimeMillis();
        log.info("application test end time is {},execution time: {} milliseconds", endTime, endTime - startTime);
    }

    @Data
    public static class Order {
        private String id;
        private String name;
    }
}
