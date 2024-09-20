package com.zzh.springboot3.algorithm;

import cn.hutool.json.JSONUtil;
import com.zzh.springboot3.service.BulkHeadService;
import com.zzh.springboot3.service.RedissonService;
import com.zzh.springboot3.service.RetryService;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import io.vertx.core.json.impl.JsonUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.junit.jupiter.api.Test;
import org.redisson.api.*;
import org.redisson.api.listener.MessageListener;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

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
        map.put(zzh3, "zzh3");
        Meter.Id zzh4 = new Meter.Id("zzh", Tags.of(Tag.of("name", "zzh1")), null, null, Meter.Type.GAUGE);
        log.info("zzh4是否存在,{}", map.containsKey(zzh4));
    }


    @Resource(name = "redisson")
    private RedissonClient redisson;

    @Test
    public void bucketTest() {
        RBucket<String> bucket = redisson.getBucket("zzh");
        bucket.set("zzh");
        log.info("bucket is :{}", bucket.get());

        RBucket<String> bucket2 = redisson.getBucket("ylm");
        bucket2.set("ylm");
        log.info("bucket2 is :{}", bucket2.get());

        RBuckets buckets = redisson.getBuckets();
        Map<String, Object> bucketMap = buckets.get("zzh", "ylm");
        log.info("buckets is :{}", JSONUtil.toJsonStr(bucketMap));
    }


    @Test
    public void mapTest() {
        RMap<String, String> map = redisson.getMap("zzhMap");
        map.put("zzh", "zzh");
        map.put("ylm", "ylm");
        map.put("zzhYlm", "zzhYlm");
        log.info("map is :{}", JSONUtil.toJsonStr(map));
    }


    @Test
    public void listTest() {
        RList<Object> rList = redisson.getList("zzhList");
        rList.add("zzh");
        rList.add("ylm");
        rList.add("zzhYlm");
        log.info("rList is :{}", JSONUtil.toJsonStr(rList));
    }


    @Test
    public void setTest() {
        RBitSet set = redisson.getBitSet("zzhSet");
        set.set(0, true);
        set.set(1812, false);
        set.set(10);
        log.info("set is :{}", JSONUtil.toJsonStr(set));
    }

    @Test
    public void topicTest() {
        RTopic topic = redisson.getTopic("zzhTopic");
        topic.addListener(String.class, (channel, msg) -> {
            log.info("channel is :{} ,message is :{}", channel, msg);
        });
        topic.publish("zzh");
        topic.publish("ylm");
        topic.publish("zzhYlm");
    }


    @Test
    public void rateLimiterTest() {
        RRateLimiter rateLimiter = redisson.getRateLimiter("myRateLimiter");
        // 初始化
        // 最大流速 = 每1秒钟产生10个令牌
        rateLimiter.trySetRate(RateType.OVERALL, 10, 1, RateIntervalUnit.SECONDS);
        rateLimiter.acquire(3);
        new Thread(() -> rateLimiter.acquire(2));
        rateLimiter.acquire();

        System.out.println(rateLimiter.availablePermits());
    }


    @Test
    public void priorityQueue() {
        RPriorityQueue<Integer> queue = redisson.getPriorityQueue("anyQueue");
//        queue.trySetComparator(Comparator.comparingInt(o -> o)); // 指定对象比较器
        queue.add(3);
        queue.add(1);
        queue.add(2);
        queue.removeAsync(0);
        System.out.println(queue.poll());
    }


    @Test
    public void countDownLatchTest() {
        RCountDownLatch latch = redisson.getCountDownLatch("anyCountDownLatch");
        latch.trySetCount(1);
        latch.countDown();
        log.info("count is :{}", latch.getCount());

        // 在其他线程或其他JVM里
//        RCountDownLatch latch2 = redisson.getCountDownLatch("anyCountDownLatch");
//        latch2.countDown();
    }

    @Resource
    private RetryService retryService;

    @Test
    public void retryTest() {
        retryService.retry();
    }

    @Test
    public void retrySpringTest() {
        retryService.retrySpring();
    }

    @Resource
    private BulkHeadService bulkHeadService;

    @Test
    public void bulkHeadTest(){
        for (int i = 0; i < 10; i++) {
            bulkHeadService.bulkHead();
        }
    }

}
