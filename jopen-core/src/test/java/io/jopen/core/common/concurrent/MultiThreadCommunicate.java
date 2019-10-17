package io.jopen.core.common.concurrent;

/**
 * 多个线程通信
 * <p>
 * TODO  Java线程之间如何通信？  JDK中自带机制
 *
 * @author maxuefeng
 */
public class MultiThreadCommunicate {

    private static int count = 0;


    public static void main(String[] args) {
        // 创建该类的对象
        // 使用匿名内部类的形式，没创建runnable对象

        Runnable runnable = new Runnable() {

            @Override
            public void run() {

                while (count < 100) {

                    // 上锁当前对象
                    synchronized (this) {

                        // 唤醒另一个线程
                        notify();

                        System.out.println("Thread " + Thread.currentThread().getName() + " " + count++);

                        try {

                            // 使其休眠100毫秒，放大线程差异
                            Thread.sleep(100);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        try {
                            // 释放掉锁
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };

        // 启动多个线程
        new Thread(runnable).start();
        new Thread(runnable).start();
        new Thread(runnable).start();

    }
}
