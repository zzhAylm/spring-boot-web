package com.zzh.springboot3.test;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.TtlRunnable;
import com.alibaba.ttl.threadpool.TtlExecutors;
import org.apache.poi.ss.formula.functions.T;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TtlTest {

    static TransmittableThreadLocal<String> ttl = new TransmittableThreadLocal<>();

    public static void main(String[] args) {
        ttl.set("hello from parent");

        // 包装线程池，关键点！
//        ExecutorService executor = TtlExecutors.getTtlExecutorService(Executors.newFixedThreadPool(1));
        ExecutorService executor = Executors.newFixedThreadPool(1);

        executor.submit(() -> {
            System.out.println("子线程拿到的值：" + ttl.get()); // 输出：hello from parent
        });

        executor.submit(() -> {
            ttl.set("hello from child");
            System.out.println("子线程拿到的值：" + ttl.get()); // 输出：hello from parent
        });

        Runnable task = () -> {
            System.out.println("拿到上下文：" + ttl.get());
        };
        // 包装任务
        Runnable wrapped = TtlRunnable.get(task);

        executor.submit(wrapped);


        executor.shutdown();


        InheritableThreadLocal<String> local = new InheritableThreadLocal<>();
        local.set("父线程的值");

        new Thread(() -> {
            System.out.println("子线程获取：" + local.get());
        }).start();



        ThreadLocal<String> local1 = new ThreadLocal<>();
        local.set("父线程的值");

        new Thread(() -> {
            System.out.println("子线程获取：" + local1.get());
        }).start();





    }
}
