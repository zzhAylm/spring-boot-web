package com.zzh.springboot3.test;

import java.util.concurrent.*;
import java.util.Arrays;

public class TaskOrchestrator {
    public static void main(String[] args) {
        // 创建一个线程池来执行任务
        ExecutorService executor = Executors.newFixedThreadPool(5);

        // 定义任务一
        Future<String> taskOneResult = executor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(2000); // 模拟耗时操作
                return "Task One Result";
            }
        });

        // 定义任务二，依赖任务一
        Future<String> taskTwoResult = executor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                String result = taskOneResult.get(); // 阻塞等待任务一完成
                Thread.sleep(1000); // 模拟耗时操作
                return "Task Two Result, got: " + result;
            }
        });

        // 定义任务三
        Future<String> taskThreeResult = executor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(1500); // 模拟耗时操作
                return "Task Three Result";
            }
        });

        // 定义任务四，依赖任务二和任务三
        Future<String> taskFourResult = executor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                String taskTwoOutput = taskTwoResult.get(); // 阻塞等待任务二完成
                String taskThreeOutput = taskThreeResult.get(); // 阻塞等待任务三完成
                Thread.sleep(500); // 模拟耗时操作
                return "Task Four Result, got: " + taskTwoOutput + " and " + taskThreeOutput;
            }
        });

        // 打印最终结果
        try {
            System.out.println("Final Result: " + taskFourResult.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
