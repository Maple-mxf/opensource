package io.jopen.core.algorithm.linked;

/**
 * 单向链表
 *
 * @author maxuefeng
 */
public class UnidirectionalLinkedList<T> extends AbstractLinkedList<T> {


    @Override
    public void add(T data) {

        Node<T> node = new Node<>(index(), data, null);

        // 下一个节点
        this.currentNode.setNext(node);

        // 当前节点赋值
        this.currentNode = node;
    }

    @Override
    public T get(int index) {
        return null;
    }
}
