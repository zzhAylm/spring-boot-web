package com.zzh.redisson.service;

import cn.hutool.json.JSONUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/12/16 20:54
 */
@Slf4j
@Service
@SpringBootTest
public class RedissonService {

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private RedissonRxClient redissonRxClient;

    @Resource
    private RedissonReactiveClient redissonReactiveClient;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    private static final String MAP_NAME = "zzh_map";

    public void redisMap() {
        RMap<String, Object> redissonClientMap = redissonClient.getMap(MAP_NAME);
        redissonClientMap.put("new", "zzh");
        log.info("redisMap is :{}", JSONUtil.toJsonStr(redissonClientMap));
    }

    public void redissonRxClientTest() {
        redissonRxClient.getMap(MAP_NAME).put("new", "zzh").subscribe(data -> log.info("redissonRxClientTest is :{}", data));
    }


    public void localCache() {
        RLocalCachedMap<Object, Object> myLocalCache = redissonClient.getLocalCachedMap("myLocalCache", LocalCachedMapOptions.defaults());

    }

    public void redissonReactiveClientTest() {
        RAtomicLongReactive atomicLong = redissonReactiveClient.getAtomicLong("long_zzh");
        Mono<Boolean> booleanMono = atomicLong.compareAndSet(0, 10);
    }

    private Integer count = 0;


    @Test
    public void redissonClientTest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1000);
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                RLock lock = redissonClient.getLock("myLock");
                try {
                    lock.lock();
                    log.info("thread is,{},count is :{}", Thread.currentThread().getName(), count);
                    count++;
                    log.info("thread is,{}, count is :{}", Thread.currentThread().getName(), count);
                } finally {
                    lock.unlock();
                    countDownLatch.countDown();
                }
            }).start();
        }
        if (countDownLatch.await(100, TimeUnit.SECONDS)) {
            log.info("countDownLatch is ok");
        }
        log.info("result count is :{}", count);

    }


    @Test
    public void redissonClientLockTest() throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(4);

        new Thread(() -> {
            RLock lock = redissonClient.getLock("myLock");
            try {
                // 一只等待，知道获取到锁，否则一直等待，获取到锁之后没有过期时间，知道任务执行完成，释放锁
                lock.lock();
                log.info("获取锁成功，{}", Thread.currentThread().getName());
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
                countDownLatch.countDown();
            }
        }).start();

        Thread.sleep(100);

        new Thread(() -> {
            try {
                RLock lock = redissonClient.getLock("myLock");
                if (!lock.tryLock()) {
                    log.info("未获取到分布式锁，thread is :{}", Thread.currentThread().getName());
                    return;
                }
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();
                }
            } finally {
                countDownLatch.countDown();
            }

        }).start();

        new Thread(() -> {

            try {
                RLock lock = redissonClient.getLock("myLock");
                if (!lock.tryLock(5000, 2000, TimeUnit.MILLISECONDS)) {
                    log.info("未获取到分布式锁，thread is :{}", Thread.currentThread().getName());
                    return;
                }
                try {
                    log.info("tryLock wait 获取所成功");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                countDownLatch.countDown();
            }

        }).start();


        new Thread(() -> {
            RLock lock = redissonClient.getLock("myLock");
            try {
                lock.lock();
                log.info("获取锁成功，{}", Thread.currentThread().getName());
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
                countDownLatch.countDown();
            }
        }).start();

        countDownLatch.await();
        log.info("分布式锁获取测试");

    }

    @Test
    public void redissonClientLockTest2() {
        RLock lock = redissonClient.getLock("myLock");
        try {
            // 显示执行锁持有的时间,没有看门狗，到期自动释放
            lock.lock(10, TimeUnit.SECONDS);
            log.info("获取锁成功，{}", Thread.currentThread().getName());
            new Thread(() -> {
                lock.lock();
                log.info("获取锁成功：{}", Thread.currentThread().getName());
                lock.unlock();
            }).start();
            while (true) {
                Thread.sleep(1000);
                log.info("任务执行完成，耗时1s");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    @Test
    public void redissonClientLockTest3() {
        RLock lock = redissonClient.getLock("myLock");
        try {
            // 开启了开门狗机制，默认是30s，默认会自动续约，每十秒更新一次锁过期时间
            lock.lock(); //如果调用unlock()方法,或者是 看门狗不续约了，锁是不会自动释放的
            log.info("获取锁成功，{}", Thread.currentThread().getName());
            new Thread(() -> {
                lock.lock();
                log.info("获取锁成功：{}", Thread.currentThread().getName());
                lock.unlock();
            }).start();
            while (true) {
                Thread.sleep(1000);
                log.info("任务执行完成，耗时1s");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }


    @Test
    public void redissonClientReadWriteLockTest() throws InterruptedException {
        RReadWriteLock readWriteLock = redissonClient.getReadWriteLock("read_write_lock");

        for (int i = 0; i < 10; i++) {

            new Thread(() -> {
                if (!readWriteLock.writeLock().tryLock()) {
                    log.info("未获取写到锁，Thread is :{}", Thread.currentThread().getName());
                    return;
                }
                try {
                    log.info("获取写到锁，Thread is :{}", Thread.currentThread().getName());
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    readWriteLock.writeLock().unlock();
                }
            }).start();
            Thread.sleep(100);
            new Thread(() -> {
                if (!readWriteLock.readLock().tryLock()) {
                    log.info("未获取到锁，Thread is :{}", Thread.currentThread().getName());
                    return;
                }
                try {
                    log.info("获取到读锁，Thread is :{}", Thread.currentThread().getName());
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    readWriteLock.readLock().unlock();
                }
            }).start();

        }

    }


    @Test
    public void redissonClientRMapTest() {
        RMap<String, String> redissonClientMap = redissonClient.getMap("redisson_map");
        redissonClientMap.put("key2", "value1");
        log.info("redisson map is :{}", JSONUtil.toJsonStr(redissonClientMap));
    }


    @Test
    public void redissonClientQueueTest() {
        RQueue<Object> redissonQueue = redissonClient.getQueue("redisson_queue");
        redissonQueue.add("zzh");
        Object poll = redissonQueue.poll();
        log.info("queue pop is :{}", poll);
    }

    @Test
    public void redissonClientSortSetTest() {
        RSortedSet<String> redissonSortSet = redissonClient.getSortedSet("redisson_sort_set");

        // 修正比较器，明确处理 null 值，避免返回 0 来表示相等
        redissonSortSet.trySetComparator(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        redissonSortSet.add("1");
        redissonSortSet.add("2");
        redissonSortSet.add("3");
        for (String string : redissonSortSet) {
            log.info("value is: {}", string);
        }
        String firstValue = redissonSortSet.first();
        String lastValue = redissonSortSet.last();

        log.info("first value is {}", firstValue);
        log.info("last value is {}", lastValue);
    }


    @Test
    public void redissonClientSourceSetTest() {
        RScoredSortedSet<String> redissonScoreSortSet = redissonClient.getScoredSortedSet("redisson_score_sort_set");
        redissonScoreSortSet.add(3, "c");
        redissonScoreSortSet.add(4, "d");
        redissonScoreSortSet.add(5, "e");
        redissonScoreSortSet.add(1, "a");
        redissonScoreSortSet.add(2, "b");
        redissonScoreSortSet.stream().forEach(value -> {
            log.info("value is :{}", value);
        });

    }


    @Test
    public void redisMapTest() {
        redisTemplate.opsForHash().put("hash_key", "name", "zzh");
        redisTemplate.opsForHash().put("hash_key", "age", "18");
        redisTemplate.opsForHash().put("hash_key", "high", "175");
        String name = (String) redisTemplate.opsForHash().get("hash_key", "name");
        String six = (String) redisTemplate.opsForHash().get("hash_key", "six");
        log.info("name is :{}", name);
        log.info("six is :{}", six);
    }


    @Test
    public void redisListTest() {
       while (true){
           String value =(String) redisTemplate.opsForList().rightPop("list_key", 10, TimeUnit.SECONDS);
           log.info("list value is :{}", value);
       }
    }


    @Test
    public void redisListLeftPushTest(){
        redisTemplate.opsForList().leftPush("list_key", "zzh");
    }


    @Test
    public void redisPublishTest(){
        String channel = new ChannelTopic("channel_key").getTopic();
        redisTemplate.convertAndSend(channel, "zzh");
    }

    @Test
    public void redisSubscribeTest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisTemplate.getConnectionFactory());
        container.addMessageListener(new MessageListenerAdapter(new RedisSubscriber(countDownLatch), "channel_key"), new PatternTopic("channel_key"));
        countDownLatch.await();
    }
    public static class RedisSubscriber implements MessageListener {

       private CountDownLatch countDownLatch;

        public RedisSubscriber(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void onMessage(Message message, byte[] pattern) {
            String channel = new String(pattern);
            String content = new String(message.getBody());
            System.out.println("订阅者收到消息：" + content + "（来自频道：" + channel + "）");
            countDownLatch.countDown();
        }
    }

    static long[] arr =new long[2];
    public static void main(String[] args) {

        System.out.println(arr[6]);


    }



}
