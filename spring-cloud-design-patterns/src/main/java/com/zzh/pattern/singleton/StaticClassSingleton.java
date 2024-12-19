package com.zzh.pattern.singleton;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/12/19 11:15
 */
// 匿名内部类方法的单例模式
public class StaticClassSingleton {

    private StaticClassSingleton() {
    }

    private static final class SingletonHolder {
        // JVM 加载机制，既保证了线程安全，有保证了懒加载
        private static final StaticClassSingleton INSTANCE =new StaticClassSingleton();
    }

    public static StaticClassSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
