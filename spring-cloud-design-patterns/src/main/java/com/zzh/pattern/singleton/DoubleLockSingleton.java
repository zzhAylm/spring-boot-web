package com.zzh.pattern.singleton;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/12/19 11:15
 */
// 双重检查锁的的单例模式
public class DoubleLockSingleton {

    private static volatile DoubleLockSingleton INSTANCE;

    private DoubleLockSingleton() {
    }


    public static DoubleLockSingleton getInstance() {
        if (INSTANCE == null) {
            synchronized (DoubleLockSingleton.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DoubleLockSingleton();
                }
            }
        }
        return INSTANCE;
    }


}
