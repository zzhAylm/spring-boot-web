package com.zzh.springboot3.desin.pattern.singleton;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/9/9 16:59
 */
public class SingletonPattern {

    private SingletonPattern() {
    }

    public static SingletonPattern getInstance() {
        return SingletonPatternHolder.instance;
    }

    private static class SingletonPatternHolder {
        //这个实现方式是静态内部类单例模式，它利用了 Java 的类加载机制来确保线程安全和延迟加载
        private static final SingletonPattern instance = new SingletonPattern();
    }

}
