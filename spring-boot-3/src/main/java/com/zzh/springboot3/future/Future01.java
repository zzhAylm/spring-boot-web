package com.zzh.springboot3.future;

import java.util.concurrent.CompletableFuture;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/6/20 15:47
 */
public class Future01 {

    public static void main(String[] args) {

        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> 10);
        completableFuture.join();

    }
}
