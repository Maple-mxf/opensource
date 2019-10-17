package io.jopen.core.common.concurrent;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 原子操作      基于CAS机制
 *
 * @author maxuefeng
 * @see java.util.concurrent.locks.Lock
 * @see io.jopen.core.common.concurrent.SpinLockTest  SpinLock test pass
 */
public class SpinLock {

    // 锁拥有者的线程
    private AtomicReference<Thread> owner = new AtomicReference<>();

    // 计数器
    private int count;

    /**
     * 加锁
     */
    public void lock() {

        // 当前线程
        Thread currentThread = Thread.currentThread();

        // 进行自旋
        while (!owner.compareAndSet(null, currentThread)) {
            // lock函数将owner设置为当前线程，并且预测原来的值为空。
            // unlock函数将owner设置为null，并且预测值为当前线程。
            // 当有第二个线程调用lock操作时由于owner值不为空，导致循环
        }
    }

    /**
     * 释放锁
     */
    public void unlock() {

        Thread currentThread = Thread.currentThread();

        // unlock函数将owner设置为null，并且预测值为当前线程。
        // 当有第二个线程调用lock操作时由于owner值不为空，导致循环
        owner.compareAndSet(currentThread, null);
    }
}
