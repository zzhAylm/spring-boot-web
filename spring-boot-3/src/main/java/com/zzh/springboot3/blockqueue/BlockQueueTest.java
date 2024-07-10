package com.zzh.springboot3.blockqueue;

import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.Semaphore;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/7/1 13:42
 */
public class BlockQueueTest {


    public static void main(String[] args) {

        Semaphore semaphore=new Semaphore(100);
        ForkJoinTask forkJoinTask= new RecursiveAction() {
            @Override
            protected void compute() {

            }
        };

        RecursiveTask<Object> objectRecursiveTask = new RecursiveTask<>() {
            @Override
            protected Object compute() {
                return null;
            }
        };


    }

}
