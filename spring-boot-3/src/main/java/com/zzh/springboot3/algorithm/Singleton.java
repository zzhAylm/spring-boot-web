package com.zzh.springboot3.algorithm;

public class Singleton {
    // 使用 volatile 关键字确保 instance 在所有线程中同步
    private static volatile Singleton instance;

    // 私有构造方法，防止外部实例化
    private Singleton() {
        // 可以在这里进行一些初始化操作
    }

    //第一次检查 if (instance == null) 是为了避免多个线程都通过第一次检查后都进入同步块，减少同步的开销。
    //同步块内的第二次检查 if (instance == null) 是为了在获取锁后再次检查实例是否为 null，以防止多个线程同时通过第一次检查后，多次创建实例。
    // 多个线程都在排队，第一个示例化好之后，第二线程排队之后直接从synchronized进入同步快，如果不加第二次if，他也会创建示例

    /***
     * 禁止指令重排序：
     *
     * 声明 instance 变量为 volatile 可以防止虚拟机在创建对象时的指令重排序，即确保在实例化对象、将引用赋给 instance 变量之前，不会进行重排序操作。
     * 保证可见性：
     *
     * 使用 volatile 修饰的变量在多线程环境下，当一个线程修改了变量的值，其他线程能够立即看到最新修改后的值，从而避免了工作内存和主内存的数据不一致问题。
     * */
    public static Singleton getInstance() {
        // 第一次检查，如果实例已经存在，则直接返回，避免不必要的同步
        if (instance == null) {
            synchronized (Singleton.class) {
                // 第二次检查，确保只有一个线程能够创建实例
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
