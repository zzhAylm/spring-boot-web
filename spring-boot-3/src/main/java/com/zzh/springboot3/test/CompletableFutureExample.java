package com.zzh.springboot3.test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureExample {

    public static void main(String[] args) {
        // 任务一：返回 "Task 1 result"
        CompletableFuture<String> task1 = CompletableFuture.supplyAsync(() -> {
            try {
                // 模拟耗时操作
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
            return "Task 1 result";
        });

        // 任务二：依赖任务一，返回 "Task 2 result" + 任务一的结果
        CompletableFuture<String> task2 = task1.handle((result1, throwable) -> {
            try {
                // 模拟耗时操作
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
            return "Task 2 result " + result1;
        });
        // 任务三：和任务一、任务二并行执行，返回 "Task 3 result"
        CompletableFuture<String> task3 = CompletableFuture.supplyAsync(() -> {
            try {
                // 模拟耗时操作
                Thread.sleep(800); // 任务三可能比任务二先完成
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
            return "Task 3 result";
        });
        // 任务四：依赖任务二和任务三，等待它们都完成后执行，返回 "Task 4 result" + 任务二和任务三的结果
        CompletableFuture<String> task4 = CompletableFuture.allOf(task2, task3).handle((res, throwable) -> {
            try {
                // 这里不需要显式等待，因为 allOf 已经保证了它们完成
                return "Task 4 result with " + task2.get() + " and " + task3.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });
        // 获取任务四的结果并打印
        String finalResult = task4.join();
        System.out.println(finalResult);
    }





}

