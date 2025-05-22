package com.zzh.ai.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.StampedLock;

/**
 * @Description:
 * @Author: zzh
 * @Create 2025/5/7 14:42
 */
@Slf4j
public class LockTest {

    private ReentrantLock reentrantLock = new ReentrantLock();

    private StampedLock stampedLock = new StampedLock();

    @Test
    public void lockTest() {
        new Thread(() -> {
            reentrantLock.lock();
            try {
                log.info("获取锁成功");
            } catch (Exception e) {
                log.error("获取锁失败", e);
            } finally {
                reentrantLock.unlock();
            }
        }).start();

        new Thread(() -> {
            reentrantLock.lock();
            try {
                log.info("获取锁成功");
            } catch (Exception e) {
                log.error("获取锁失败", e);
            } finally {
                reentrantLock.unlock();
            }
        }).start();
    }

    @Test
    public void stampedLockTest() {
        new Thread(() -> {
            long stamp = stampedLock.writeLock();
            try {
                log.info("获取写锁成功");
                Thread.sleep(1000);
            } catch (Exception e) {
                log.error("获取锁失败", e);
            } finally {
                stampedLock.unlockWrite(stamp + 1);
            }
        }).start();

        new Thread(() -> {
            long stamp = 0;
            try {
                stamp = stampedLock.tryReadLock(2000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                log.info("获取锁成功");
            } catch (Exception e) {
                log.error("获取锁失败", e);
            } finally {
                stampedLock.unlockRead(stamp);
            }
        }).start();

    }


    private double x, y;
    private final StampedLock sl = new StampedLock();

    // an exclusively locked method
    void move(double deltaX, double deltaY) {
        long stamp = sl.writeLock();
        try {
            x += deltaX;
            y += deltaY;
        } finally {
            sl.unlockWrite(stamp);
        }
    }

    // a read-only method
    // upgrade from optimistic read to read lock
    double distanceFromOrigin() {
        long stamp = sl.tryOptimisticRead();
        try {
            retryHoldingLock:
            for (; ; stamp = sl.readLock()) {
                if (stamp == 0L)
                    continue retryHoldingLock;
                // possibly racy reads
                double currentX = x;
                double currentY = y;
                if (!sl.validate(stamp))
                    continue retryHoldingLock;
                return Math.hypot(currentX, currentY);
            }
        } finally {
            if (StampedLock.isReadLockStamp(stamp))
                sl.unlockRead(stamp);
        }
    }

    // upgrade from optimistic read to write lock
    void moveIfAtOrigin(double newX, double newY) {
        long stamp = sl.tryOptimisticRead();
        try {
            retryHoldingLock:
            for (; ; stamp = sl.writeLock()) {
                if (stamp == 0L)
                    continue retryHoldingLock;
                // possibly racy reads
                double currentX = x;
                double currentY = y;
                if (!sl.validate(stamp))
                    continue retryHoldingLock;
                if (currentX != 0.0 || currentY != 0.0)
                    break;
                stamp = sl.tryConvertToWriteLock(stamp);
                if (stamp == 0L)
                    continue retryHoldingLock;
                // exclusive access
                x = newX;
                y = newY;
                return;
            }
        } finally {
            if (StampedLock.isWriteLockStamp(stamp))
                sl.unlockWrite(stamp);
        }
    }

    // upgrade read lock to write lock
    void moveIfAtOrigin2(double newX, double newY) {
        long stamp = sl.readLock();
        try {
            while (x == 0.0 && y == 0.0) {
                long ws = sl.tryConvertToWriteLock(stamp);
                if (ws != 0L) {
                    stamp = ws;
                    x = newX;
                    y = newY;
                    break;
                } else {
                    sl.unlockRead(stamp);
                    stamp = sl.writeLock();
                }
            }
        } finally {
            sl.unlock(stamp);
        }
    }


}
