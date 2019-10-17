package io.jopen.core.common.collection;

import java.lang.reflect.Array;

/**
 * 有序数组实现优先级队列
 *
 * @author maxuefeng
 */
public class SortedArrayQueue<T> extends AbstractQueue<T> {

    // 有序数组

    private Node<T>[] origin;

    // 初始化容量
    private static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;


    public SortedArrayQueue(Class<T> type) {

        // 泛型数组初始化
        origin = (Node<T>[]) Array.newInstance(type,DEFAULT_INITIAL_CAPACITY);
    }


    @Override
    public void enQueue(T data) {

    }

    @Override
    public T deQueue() {
        return null;
    }
}
