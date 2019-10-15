package core.algorithm.linked;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author maxuefeng
 */
public abstract class AbstractLinkedList<T> implements Linked<T> {

    // 当前节点
    protected Node<T> currentNode;

    // 当前节点索引
    private AtomicInteger index = new AtomicInteger(0);

    @Override
    public int index() {
        return index.getAndIncrement();
    }
}
