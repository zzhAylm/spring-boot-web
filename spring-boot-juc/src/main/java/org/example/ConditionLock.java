package org.example;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description:
 * @Author: zzh
 * @Create 2026/8/5 13:42
 */
public class ConditionLock {
    private static int count = 10;

    private static int state=0;
    static Lock lock = new ReentrantLock();

    static Condition condition1 = lock.newCondition();
    static Condition condition2 = lock.newCondition();
    static Condition condition3 = lock.newCondition();

    public static void main(String[] args) {

        new Thread(()->{
            for (int i = 0; i < count; i++) {
                lock.lock();
                try {
                    while (state!=0){
                        condition1.await();
                    }
                    System.out.println("aaaaa");
                    state=1;
                    condition2.signal();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();
                }

            }

        }).start();


        new Thread(()->{
            for (int i = 0; i < count; i++) {
                lock.lock();
                try {
                    while (state!=1){
                        condition2.await();
                    }
                    System.out.println("bbbbb");
                    state=2;
                    condition3.signal();

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();
                }

            }


        }).start();

        new Thread(()->{
            for (int i = 0; i < count; i++) {
                lock.lock();
                try {
                    while (state!=2){
                        condition3.await();
                    }
                    System.out.println("ccccc");
                    state=0;
                    condition1.signal();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();
                }

            }

        }).start();
    }
}
