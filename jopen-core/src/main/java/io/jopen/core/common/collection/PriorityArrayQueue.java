package io.jopen.core.common.collection;

/**
 * 无序数组实现优先级队列
 *
 * @author maxuefeng
 */
public class PriorityArrayQueue<T> extends AbstractQueue<T> {


    // The max size of the queue
    private int maxSize;

    // The array for the queue
    private int[] queueArray;

    //
    private int nItems;

    /**
     * @param size
     */
    public PriorityArrayQueue(int size) {
        maxSize = size;
        queueArray = new int[size];
        nItems = 0;
    }

    /**
     * 插入
     *
     * @param value
     */
    public void insert(int value) {
        if (isFull()) {
            throw new RuntimeException("Queue is full");
        }
        if (nItems == 0) {
            queueArray[0] = value;
        } else {

            int j = nItems;

            while (j > 0 && queueArray[j - 1] > value) {
                queueArray[j] = queueArray[j - 1];
                j--;
            }
            queueArray[j] = value;
        }
        nItems++;
    }

    /**
     * @return The element removed
     */
    public int remove() {
        return queueArray[--nItems];
    }

    /**
     * @return element at the front of the queue
     */
    public int peek() {
        return queueArray[nItems - 1];
    }

    /**
     * @return
     */
    public boolean isEmpty() {
        return (nItems == 0);
    }


    /**
     * @return
     */
    public boolean isFull() {
        return (nItems == maxSize);
    }

    /**
     * @return
     */
    public int getSize() {
        return nItems;
    }


    @Override
    public void enQueue(T data) {

    }

    @Override
    public T deQueue() {
        return null;
    }
}
