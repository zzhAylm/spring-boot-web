package com.zzh.springboot.algorithm;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CfAllOfDemo {

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(5);

        // 提交 5 个异步任务，每个返回一个整数结果
        List<CompletableFuture<Integer>> futures = IntStream.rangeClosed(1, 5)
                .mapToObj(i -> CompletableFuture.supplyAsync(() -> {
                    // 模拟业务
                    sleep(100L * i);
                    return i * 10; // 结果：10,20,30,40,50
                }, pool))
                .collect(Collectors.toList());

        // 等 5 个全部完成（返回的是 CompletableFuture<Void>）
        CompletableFuture<Void> all = CompletableFuture.allOf(
                futures.toArray(new CompletableFuture[0])
        );

        // all 完成后，收集每个 future 的结果并统计
        CompletableFuture<Integer> sumFuture = all.thenApply(v ->
                futures.stream()
                        .map(CompletableFuture::join) // 此时已完成，join 不会阻塞很久
                        .reduce(0, Integer::sum)
        );

        Integer sum = sumFuture.join();
        System.out.println("总和 = " + sum); // 150

        pool.shutdown();
    }

    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
