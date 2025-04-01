package com.zzh.springboot3.pattern;

import io.swagger.models.auth.In;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description:
 * @Author: zzh
 * @Create 2025/4/1 10:31
 */
public class ConsumerProducer {


    private int capacity;

    private LinkedList<Integer> values;

    private Lock lock;

    private Condition notEmpty;

    private Condition notFull;

    public ConsumerProducer(int capacity, LinkedList<Integer> values) {
        this.capacity = capacity;
        this.values = values;
        this.lock = new ReentrantLock();
        this.notEmpty = lock.newCondition();
        this.notFull = lock.newCondition();
    }


    public void producer(Integer value) {
        lock.lock();
        try {
            while (capacity == values.size()) {
                notFull.await();
            }
            values.add(value);
            notEmpty.signalAll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public int consumer() {
        lock.lock();
        try {
            while (values.size() == 0) {
                notEmpty.await();
            }
            Integer value = values.removeLast();
            notEmpty.signalAll();
            return value;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

}
