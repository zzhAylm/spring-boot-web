package com.zzh.springboot3.pattern;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumer{

    private int capacity;
    private LinkedList<Integer> values;

    private Lock lock;
    private Condition notEmpty;
    private Condition notFull;

    public ProducerConsumer(int capacity){
        this.capacity=capacity;
        this.values=new LinkedList<>();
        this.lock=new ReentrantLock();
        this.notEmpty=lock.newCondition();
        this.notFull=lock.newCondition();
    }


    public void producer(int value){
        lock.lock();
        try{
            while(values.size()==capacity){
                notFull.await();
            }
            values.add(value);
            notEmpty.signalAll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally{
            lock.unlock();
        }
    }

    public int consumer(){
        lock.lock();
        try{
            while(values.size()==0){
                notEmpty.await();
            }
            int value=values.removeLast();
            notFull.signalAll();
            return value;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally{
            lock.unlock();
        }
    }
}

