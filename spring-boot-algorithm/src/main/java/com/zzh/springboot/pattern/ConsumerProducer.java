package com.zzh.springboot.pattern;

import java.util.LinkedList;

/**
 * @Description:
 * @Author: zzh
 * @Create 2025/3/17 13:40
 */

public class ConsumerProducer {


    private LinkedList<Integer> queue = new LinkedList<>();

    private Integer capacity = 10;



    public synchronized Integer consumer() throws InterruptedException {
        while (queue.size() == 0) {
            this.wait();
        }
        Integer value = queue.removeFirst();
        this.notifyAll();
        return value;
    }

    public synchronized void producer(Integer value) throws InterruptedException {
        while (queue.size() == capacity) {
            this.wait();
        }
        queue.addLast(value);
        this.notifyAll();
    }

    public void startConsumer() {
        new Thread(() -> {
            while (true) {
                try {
                    Integer value = consumer();
                    System.out.println("消费：" + value);
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
                    producer(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
