package core.algorithm.linked;

/**
 * 双向链表
 *
 * @author maxuefeng
 */
public class DoublyLinkedList<T> extends AbstractLinkedList<T> {



    @Override
    public void add(T data) {

        Node<T> nodeNode = new Node<>(index(), data, null);

        this.currentNode.setNext(nodeNode);
    }

    @Override
    public T get(int index) {
        return null;
    }
}
