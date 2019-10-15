package core.common.collection;

import java.io.Serializable;

/**
 * @author maxuefeng
 */
public interface Queue<T> {

    /**
     * 入队列
     */
    void enQueue(T data);

    /**
     * 出队列
     *
     * @return
     */
    T deQueue();

    /**
     *
     * @param <T>
     */
    class Node<T> implements Serializable {

        private int index;

        private T data;

        private Node<T> lNode;

        private Node<T> rNode;

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

        public Node<T> getlNode() {
            return lNode;
        }

        public void setlNode(Node<T> lNode) {
            this.lNode = lNode;
        }

        public Node<T> getrNode() {
            return rNode;
        }

        public void setrNode(Node<T> rNode) {
            this.rNode = rNode;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "index=" + index +
                    ", data=" + data +
                    ", lNode=" + lNode +
                    ", rNode=" + rNode +
                    '}';
        }
    }
}
