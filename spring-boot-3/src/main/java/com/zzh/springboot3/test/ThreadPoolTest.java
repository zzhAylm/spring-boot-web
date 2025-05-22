package com.zzh.springboot3.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

/**
 * @Description:
 * @Author: zzh
 * @Create 2025/5/19 15:14
 */
@Slf4j
public class ThreadPoolTest {

    ThreadPoolExecutor executor = new ThreadPoolExecutor(
            1,
            1,
            1000,
            TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<Runnable>(100));


    @Test
    public void execTest() {
//        executor.execute(() -> {
//            throw new RuntimeException("execute运行异常");
//        });
//
//        // 当使用 execute 方法时，如果遇到未处理的异常，会抛出未捕获的异常，并将当前线程进行销毁。
//        executor.execute(() -> {
//            log.info("继续执行任务");
//        });
//        executor.execute(() -> {
//            log.info("继续执行任务");
//        });


        Future<Object> future = executor.submit(() -> {
            throw new RuntimeException("execute运行异常");
        });
        try {
            log.info("result is :{}", future.get());
        } catch (InterruptedException | ExecutionException e) {
            log.error("自线程抛出异常", e);
        }

        executor.submit(() -> {
            log.info("继续执行任务");
        });
    }




    @Test
    public void bloomTest(){


    }
}
