package com.zzh.springboot3.algorithm;

import cn.hutool.crypto.digest.MD5;
import cn.hutool.json.JSONUtil;
import com.zzh.springboot3.listener.ApplicationPublish;
import com.zzh.springboot3.listener.CustomApplicationEvent;
import com.zzh.springboot3.service.BulkHeadService;
import com.zzh.springboot3.service.RedissonService;
import com.zzh.springboot3.service.RetryService;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import io.vertx.core.json.impl.JsonUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.ognl.security.OgnlSecurityManager;
import org.apache.poi.ss.formula.functions.T;
import org.junit.jupiter.api.Test;
import org.redisson.api.*;
import org.redisson.api.listener.MessageListener;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.JceSecurityManager;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

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
//        retryService.retrySpring();
    }

    @Resource
    private BulkHeadService bulkHeadService;

    @Test
    public void bulkHeadTest() {
        for (int i = 0; i < 10; i++) {
            bulkHeadService.bulkHead();
        }
    }

    @Test
    public void mapUsedTest() {
        Map<String, String> stringMap = new ConcurrentHashMap<>();
        // 如果不能再存，就进行覆盖
        stringMap.putIfAbsent("zzh", "zzh");

        // 如果Map 通过旧值计算新值，并进行替换
        stringMap.compute("zzh", (k, oldValue) -> oldValue + "zzh");

        // 如果Map中存在key，才进行操作
        stringMap.computeIfPresent("ylm", (k, oldValue) -> oldValue + "zzh");

        //如果Map中不存在key,才进行操作
        stringMap.putIfAbsent("zzhYlm_absent", "zzh");
        stringMap.putIfAbsent("ylm_absent", "zzh");
        stringMap.putIfAbsent("ylm", "absent");
        log.info("{}", stringMap);
    }

    @Resource
    private ApplicationPublish applicationPublish;


    @Test
    public void applicationListenerTest() {
        applicationPublish.publishEvent(new CustomApplicationEvent(ApplicationTest.class));
    }

    @Test
    public void encryptTest() {
        long start = System.currentTimeMillis();
        String digest = MD5.create().digestHex16("zzhAylm");
        log.info("digest is {},耗时：{} ", digest, System.currentTimeMillis() - start);
    }


    ThreadPoolExecutor productThreadPoolExecutor = new ThreadPoolExecutor(1,
            1,
            1,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(1),
            new MyThreadFactory("product"),
            new MyRejectedPolicy());

    @Test
    public void threadPoolTest() throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        new Thread(() -> {
            ArrayList<Future<Integer>> futureList = new ArrayList<>();
            //从数据库获取产品信息
            int productNum = 5;
            for (int i = 0; i < productNum; i++) {
                try {
                    int finalI = i;
                    Future<Integer> future = productThreadPoolExecutor.submit(() -> {
                        System.out.println("Thread.currentThread().getName() = " + Thread.currentThread().getName());
                        return finalI * 10;
                    });
                    futureList.add(future);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            for (Future<Integer> integerFuture : futureList) {
                try {
                    Integer integer = integerFuture.get();
                    System.out.println(integer);
                    System.out.println("future.get() = " + integer);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    @Test
    public void executorsTest() {
        // 队列大小无限
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        //队列大小无限
        ExecutorService executorService1 = Executors.newFixedThreadPool(1);
        // 线程大小无限
        ExecutorService executorService2 = Executors.newCachedThreadPool();
        //队列大小无限
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        //队列大小无限
        ScheduledExecutorService scheduledExecutorService1 = Executors.newSingleThreadScheduledExecutor();
    }


    private static class MyThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;
        private final String threadFactoryName;

        public String getThreadFactoryName() {
            return threadFactoryName;
        }

        MyThreadFactory(String threadStartName) {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            namePrefix = threadStartName + "-pool-" +
                    poolNumber.getAndIncrement() +
                    "-thread-";
            threadFactoryName = threadStartName;
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }

    private static class MyRejectedPolicy implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            if (e.getThreadFactory() instanceof MyThreadFactory myThreadFactory) {
                if ("product".equals(myThreadFactory.getThreadFactoryName())) {
                    System.out.println("线程池有任务被拒绝了,请关注");
                }
            }
        }
    }


    @Test
    public void threadPoolShoutDownTest() {
        List<Callable<Void>> tasks = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            tasks.add(() -> {
                System.out.println("callable " + finalI);
                Thread.sleep(500);
                return null;
            });
        }
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Thread executorInvokerThread = new Thread(() -> {
            try {
                executor.invokeAll(tasks);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("invokeAll returned");
        });
        executorInvokerThread.start();

        log.info("主线程");
        executor.shutdown();
        log.info("主线程完成并退出");
    }
}
