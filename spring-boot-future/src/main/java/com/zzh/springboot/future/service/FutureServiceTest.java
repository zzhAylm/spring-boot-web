package com.zzh.springboot.future.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2025/1/15 11:01
 */
@Slf4j
@SpringBootTest
public class FutureServiceTest {

    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));

    @Test
    public void futureTest() throws ExecutionException, InterruptedException {
        Future<String> submit = threadPoolExecutor.submit(() -> {
            return "";
        });
        String result = submit.get();
        log.info("result is :{}", result);
    }

    @Test
    public void completableFutureTest() {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            log.info("result is :{}", "zzh");
            return "zzh";
        });

        completableFuture.whenCompleteAsync((t, u) -> {
            log.info("t is :{},u is :{}", t, u);
        });

    }

    @Test
    public void completableFutureTest2() {
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.completedFuture("hello!")
                .thenApply(s -> s + "world!")
                .thenAccept((res) -> {
                    log.info("res is :{}", res);
                });
    }

    @Test
    public void completableFutureTest3() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "hello!")
                .whenComplete((res, ex) -> {
                    // res 代表返回的结果
                    // ex 的类型为 Throwable ，代表抛出的异常
                    System.out.println(res);
                    // 这里没有抛出异常所有为 null
                    assertNull(ex);
                });
    }

    @Test
    public void exceptionTest() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future
                = CompletableFuture.supplyAsync(() -> {
            if (true) {
                throw new RuntimeException("Computation error!");
            }
            return "hello!";
        }).handle((res, ex) -> {
            // res 代表返回的结果
            // ex 的类型为 Throwable ，代表抛出的异常
            return res != null ? res : "world!";
        });
        assertEquals("world!", future.get());
    }

    @Test
    public void completableFutureTest4() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "zzh";
        });

        while (!completableFuture.isDone()) {
            log.info("wait....");
        }
        log.info("result is :{}", completableFuture.get());
    }

}
