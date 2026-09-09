package com.zzh.springboot.algorithm;

/**
 * 经典线程死锁：两个线程以相反顺序获取同两把锁。
 * <p>
 * 线程1：先锁 lockA，再锁 lockB<br>
 * 线程2：先锁 lockB，再锁 lockA<br>
 * 运行后两线程会互相等待对方释放锁，程序卡住；可用 jstack &lt;pid&gt; 查看 "Found one Java-level deadlock"。
 *
 * @Author: zzh
 */
public class DeadlockDemo {

    private static final Object lockA = new Object();
    private static final Object lockB = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            synchronized (lockA) {
                System.out.println("线程1 持有 lockA，尝试获取 lockB...");
                sleep(100);
                synchronized (lockB) {
                    System.out.println("线程1 获取 lockB 成功");
                }
            }
        }, "thread-1");

        Thread t2 = new Thread(() -> {
            synchronized (lockB) {
                System.out.println("线程2 持有 lockB，尝试获取 lockA...");
                sleep(100);
                synchronized (lockA) {
                    System.out.println("线程2 获取 lockA 成功");
                }
            }
        }, "thread-2");

        t1.start();
        t2.start();
        t1.join(5_000);
        t2.join(5_000);
        if (t1.isAlive() || t2.isAlive()) {
            System.out.println("发生死锁：至少有一个线程仍在运行且无法继续");
        }
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
