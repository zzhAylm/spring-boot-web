package org.example;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ABCPrint {

    private static final ReentrantLock lock = new ReentrantLock();

    private static final Condition conditionA = lock.newCondition();
    private static final Condition conditionB = lock.newCondition();
    private static final Condition conditionC = lock.newCondition();

    // 当前应该执行哪个线程
    private static int state = 0;


    static class PrintA implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                lock.lock();
                try {

                    while (state != 0) {
                        conditionA.await();
                    }

                    System.out.println("A");

                    state = 1;
                    conditionB.signal();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }


    static class PrintB implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {

                lock.lock();

                try {

                    while (state != 1) {
                        conditionB.await();
                    }

                    System.out.println("B");

                    state = 2;
                    conditionC.signal();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }


    static class PrintC implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {

                lock.lock();

                try {

                    while (state != 2) {
                        conditionC.await();
                    }

                    System.out.println("C");

                    state = 0;
                    conditionA.signal();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }


    public static void main(String[] args) {

        new Thread(new PrintA()).start();
        new Thread(new PrintB()).start();
        new Thread(new PrintC()).start();

    }
}
