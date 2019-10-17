package io.jopen.core.common.concurrent;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Hashtable属于线程安全类
 * Hashtable的线程安全主要是以来与可重入锁(synchronized)实现
 * <p>
 * <p>
 * 以下方法按照效率排序：threadSafeMap1 threadSafeMap4 threadSafeMap2 threadSafeMap3
 *
 * @author maxuefeng
 * @see Hashtable#put(Object, Object)
 * @see Hashtable#get(Object)
 */
public class HashTableConcurrentTest {

    /**
     * HashMap线程安全实现方式1  利用synchronized
     */
    public void threadSafeMap1() {

        Map<String, Object> m = new HashMap<>();

        synchronized (this) {
            m.put("k", "v");
        }
    }

    /**
     * HashMap线程安全实现方式2  利用SynchronizedMap内部类实现
     * SynchronizedMap再方法内部加锁实现线程安全
     *
     * @see Collections.SynchronizedMap
     */
    public void threadSafeMap2() {
        Map<String, Object> oldMap = new HashMap<>();

        Map<String, Object> newMap = Collections.synchronizedMap(oldMap);
    }


    /**
     * https://www.jianshu.com/p/e10bde0f3cff
     * HashMap线程安全实现方式3  ConcurrentHashMap内部实现的原理是分段锁
     * 分段机制：segment，每段加reentrantLock可重入锁
     * 1 找segment数组下标 2 找segment的HashEntry数组下标
     * get方法：不需要加锁，value值使用了volatile关键字修饰
     * put方法：hash计算段---锁定段---hash计算HashEntry数组---若超多阈值---扩容---释放；put过程中会modCount+1，为了后续的计算大小
     *
     * @see ConcurrentHashMap
     * @see java.util.concurrent.ConcurrentNavigableMap
     */
    public void threadSafeMap3() {
        Map<String, Object> m1 = new ConcurrentHashMap<>();
        Map<String, Object> m2 = new ConcurrentSkipListMap<>();
    }

    /**
     * Hashtable的线程安全依赖于同步方法
     *
     * @see Hashtable
     */
    public void threadSafeMap4() {
        Map<String, Object> t = new Hashtable<>();
    }
}


















