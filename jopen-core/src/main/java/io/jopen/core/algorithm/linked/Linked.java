package io.jopen.core.algorithm.linked;

import java.io.Serializable;

/**
 * @author maxuefeng
 */
public interface Linked<T> extends Serializable {

    /**
     * 获取自增索引
     *
     * @return
     */
    int index();

    /**
     * 添加节点
     *
     * @param data
     */
    void add(T data);

    /**
     * 获取某个对象
     *
     * @param index
     * @return
     */
    T get(int index);


    /**
     * 链表节点
     *
     * @param <T>
     */
    class Node<T> implements Serializable {

        // 节点index
        private int index;

        // 节点数据
        private T data;

        // 下一个Node节点
        private Node<T> next;

        public Node(int index, T data, Node<T> next) {
            this.index = index;
            this.data = data;
            this.next = next;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "index=" + index +
                    ", data=" + data +
                    ", next=" + next +
                    '}';
        }
    }
}
