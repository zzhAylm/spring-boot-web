package com.zzh.springboot.pattern;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description:
 * @Author: zzh
 * @Create 2025/3/18 10:46
 */
public class ConsumerProducerPattern {

    private Object[] queue;

    private int capacity;

    private int putIndex;

    private int takeIndex;

    private int count;

    private Lock lock;

    private Condition notFull;

    private Condition notEmpty;

//    private LinkedList<Integer> queueList = new LinkedList<>();


    public ConsumerProducerPattern(int capacity) {
        this.capacity = capacity;
        this.queue = new Object[capacity];
        this.putIndex = 0;
        this.takeIndex = 0;
        this.count = 0;
        this.lock = new ReentrantLock();
        this.notFull = lock.newCondition();
        this.notEmpty = lock.newCondition();
    }

    public int consumer() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) {
                notEmpty.await();
            }
            int value = (int) queue[takeIndex];
            count--;
            takeIndex = (takeIndex + 1) % capacity;
            notFull.signalAll();
            return value;
        } finally {
            lock.unlock();
        }
    }

    public void producer(int value) throws InterruptedException {
        lock.lock();
        try {
            while (count == capacity) {
                notFull.await();
            }
            queue[putIndex] = value;
            count++;
            putIndex = (putIndex + 1) % capacity;
            notEmpty.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void startConsumer() {
        new Thread(() -> {
            while (true) {
                try {
                    int value = consumer();
                    System.out.println("consumer:" + value);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void startProducer() {
        new Thread(() -> {
            while (true) {
                try {
                    producer((int) (Math.random() * 100));
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


}
