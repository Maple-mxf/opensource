package io.jopen.core.common.collection;

import java.util.Comparator;

/**
 * 基于完全二叉树实现优先级队列
 * <p>
 * 把元素加入进来
 * 然后上游元素到合适的位置
 * [https://juejin.im/post/5a324c08f265da431f4b18ac]
 *
 * @author maxuefeng
 * @see Comparator
 * @see io.jopen.core.algorithm.tree.CompleteBinaryTree
 */
public class PriorityQueue<T extends Comparator<T>> extends AbstractQueue<T> {

    Node<T> root;


    public PriorityQueue(Node<T> root) {
        this.root = root;
    }

    public PriorityQueue() {
    }


    /**
     * 添加元素
     * 1 在元素加入进来后，与其父结点比较，假如大于父结点，则把元素上游到父结点的位置。
     * 2 在上游到父结点位置后，再和当前所处结点的父结点比较，如果大于父结点，继续上游。
     * 3 重复执行，直到整棵树调整成为大顶堆。
     *
     * @param data
     */
    @Override
    public void enQueue(T data) {

    }

    /**
     * 交换首尾两个元素的位置，这样尾元素将会成为根结点，堆有序被打破。
     * 剪掉被交换到末尾的原根元素
     * 把交换到根结点的元素下沉到合适位置，重新调整为大顶堆
     * 返回被剪出的元素，即为需要出列的最大优先值元素
     * @return
     */
    @Override
    public T deQueue() {
        return null;
    }
}
