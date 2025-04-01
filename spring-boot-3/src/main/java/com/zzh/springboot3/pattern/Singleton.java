package com.zzh.springboot3.pattern;

/**
 * @Description:
 * @Author: zzh
 * @Create 2025/3/31 21:08
 */
public class Singleton {

    private Singleton() {
    }

    public static Singleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final Singleton INSTANCE = new Singleton();
    }
}
