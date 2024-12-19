package com.zzh.pattern.observer;

/**
 * @Description: 观察者模式
 * @Author: zzh
 * @Crete 2024/12/19 17:59
 */
public class Listener {
    void onEvent(Event event) {
        System.out.println("监听器：" + event);
    }
}
