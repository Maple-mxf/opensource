package io.jopen.core.common.concurrent;

/**
 * SpinLock测试
 * @author maxuefeng
 * @see SpinLock
 */
public class SpinLockTest implements Runnable {

    // not safe var
    public static int num = 0;

    // CAS lock
    private SpinLock lock;

    public SpinLockTest(SpinLock lock) {
        this.lock = lock;
    }

    @Override
    public void run() {

        // 对当前线程枷锁
        this.lock.lock();

        // 修改不安全变量
        num++;

        // 释放锁
        this.lock.unlock();
    }

    /**
     * test pass
     *
     * @param args
     * @throws InterruptedException
     * @see SpinLock
     */
    public static void main(String[] args) throws InterruptedException {
        //
        SpinLock lock = new SpinLock();

        for (int i = 0; i < 90; i++) {
            // 开启线程执行
            new Thread(new SpinLockTest(lock)).start();
        }

        // 主线程睡眠
        Thread.sleep(10000);

        // num的理论值为90
        System.out.println(num);
    }
}
