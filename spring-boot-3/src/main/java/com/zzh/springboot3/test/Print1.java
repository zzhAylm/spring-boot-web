package com.zzh.springboot3.test;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description:
 * @Author: zzh
 * @Create 2025/4/16 21:00
 */
public class Print1 {


    private static ReentrantLock lock = new ReentrantLock();

    private static Condition conditionA = lock.newCondition();
    private static Condition conditionB = lock.newCondition();
    private static Condition conditionC = lock.newCondition();


    private volatile static int state = 0; // 0 -> A, 1 -> B, 2 -> C
    private static final char[] str = new char[9];

    public static void main(String[] args) {

        new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                lock.lock();
                try {
                    while (state % 3 != 0) {
                        conditionA.await();
                    }
                    str[state] = 'A';
                    state++;
                    conditionB.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                lock.lock();
                try {
                    while (state % 3 != 1) {
                        conditionB.await();
                    }
                    str[state] = 'B';
                    state++;
                    conditionC.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                lock.lock();
                try {
                    while (state % 3 != 2) {
                        conditionC.await();
                    }
                    str[state] = 'C';
                    state++;
                    conditionA.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
            System.out.println("结果: " + Arrays.toString(str));
        }).start();


    }
}
