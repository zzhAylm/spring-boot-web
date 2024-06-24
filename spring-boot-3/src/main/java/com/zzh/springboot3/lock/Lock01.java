package com.zzh.springboot3.lock;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/6/24 10:23
 */
public class Lock01 {

    public static void main(String[] args) {

        AbstractQueuedSynchronizer abstractQueuedSynchronizer;
        Thread thread = new Thread(() -> {

        });
        thread.setPriority(5);
        thread.setDaemon(false);
        System.out.println(thread.getName());
        System.out.println(thread.getThreadGroup().getName());
    }
}
