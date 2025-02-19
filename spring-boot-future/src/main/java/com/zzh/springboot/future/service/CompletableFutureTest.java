package com.zzh.springboot.future.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

/**
 * @Description:
 * @Author: zzh
 * @Create 2025/2/12 14:04
 */
@Slf4j
public class CompletableFutureTest {

    @Test
    public void completableFutureTest() {
        CompletableFuture.runAsync(() -> {
            log.info("CompletableFuture runAsync,没有返回值");
        }).whenComplete((t, u)->{
            log.info("t is :{}, u is :{}", t, u);
        });
        CompletableFuture.supplyAsync(() -> {
            log.info("CompletableFuture supplyAsync,又返回值");
            return "zzh";
        }).whenComplete((t, u) -> {
            log.info("t is :{}, u is :{}", t, u);
        });
    }

    @Test
    public void completableFutureTest2() {
        // whenComplete 和 whenCompleteAsync 的区别
        // 执行任务和 执行回调函数 用的是否是同步
        CompletableFuture.supplyAsync(() -> {
            log.info("CompletableFuture supplyAsync,又返回值");
            return "whenComplete";
        }).whenComplete((t, u) -> {
            log.info("CompletableFuture whenComplete,和执行任务用的同一个线程");
            log.info("t is :{}, u is :{}", t, u);
        });

        CompletableFuture.supplyAsync(() -> {
            log.info("CompletableFuture supplyAsync,又返回值");
            return "whenCompleteAsync";
        }).whenCompleteAsync((t, u) -> {
            log.info("CompletableFuture whenComplete,和执行任务supplyAsync用的不是同一个线程");
            log.info("t is :{}, u is :{}", t, u);
        });
    }



    @Test
    public void completableFutureTest3() {

        /**
         * thenApply：转换结果并继续异步处理。
         * thenAccept：消费结果（例如打印日志、更新 UI）而不关心结果类型。
         * thenRun：执行额外的操作，不关心结果（例如清理操作）。
         * thenCombine：需要并行执行两个任务并合并结果的场景。
         * whenComplete：用于处理任务完成后的后续操作，不论成功或失败。
         * thenCompose：将异步操作串联起来，每个操作依赖前一个操作的结果。
         * **/
        // completableFuture3 依赖 completableFuture1
        CompletableFuture<String> completableFuture1 = CompletableFuture.supplyAsync(() -> {
            log.info("completableFuture1");
            return "completableFuture1";
        });

        CompletableFuture<String> completableFuture2 = CompletableFuture.supplyAsync(() -> {
            return "completableFuture2";
        });

        CompletableFuture<String> completableFuture3 = completableFuture1.thenApply((t) -> {
            log.info("thenApply");
            return "thenApply";
        });

        CompletableFuture<String> cf3 = completableFuture1.thenApply(result1 -> {
            return "result3";
        });

        // cf4 依赖 completableFuture1 和 completableFuture2的结果
        CompletableFuture<String> cf4 = completableFuture1.thenCombine(completableFuture2, (result1, result2) -> {
            //result1和result2分别为cf1和cf2的结果
            return "result4";
        });


        // 多个依赖 cf6 依赖 cf3，cf4
        CompletableFuture<Void> cf6 = CompletableFuture.allOf(cf3, cf4);
        CompletableFuture<String> result = cf6.thenApply(v -> {
            //这里的join并不会阻塞，因为传给thenApply的函数是在CF3、CF4、CF5全部完成时，才会执行 。
            String result3 = cf3.join();
            String result4 = cf4.join();
            //根据result3、result4、result5组装最终result;
            return "result";
        });
//        cf6.thenrun

        result.thenAccept((t->{

            log.info("操作结果，cf6 result is :{}",t);
        }));
    }


}
