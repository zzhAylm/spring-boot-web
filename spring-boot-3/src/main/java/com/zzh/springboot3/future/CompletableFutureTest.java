package com.zzh.springboot3.future;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/10/16 14:52
 */
@Slf4j
public class CompletableFutureTest {

    @Test
    public void completableFutureTest() throws ExecutionException, InterruptedException {
        CompletableFuture.runAsync(()->{

        });
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "zzh");
       log.info("completableFuture.get() is :{}",completableFuture.get());

        CompletableFuture<Object> objectCompletableFuture = new CompletableFuture<>();

        boolean complete = objectCompletableFuture.complete(objectCompletableFuture);
    }







}
