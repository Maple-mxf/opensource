package io.jopen.core.common.collection;

/**
 * 基于完全二叉树实现优先级队列
 * <p>
 * 完全二叉树(堆)。假如我们能保证二叉树的每一个父结点的优先值都大于或者等于它的两个子结点，那么在整棵树看来，
 * 顶部根结点必定就是优先值最大的。这样的树结构可以称为堆有序，并且因为最大值在根部，也称为大顶堆。
 * 在每次出队操作时，只需要把根结点出队即可，然后重新调整二叉树恢复堆有序；在每次入队操作时把元素追加到末尾，
 * 同样调整二叉树恢复堆有序。
 * <p>
 *
 * @author maxuefeng
 */
public class LinkedList {

    /**
     */
    private Node head;



    /**
     * @param x
     *
     */
    public void insertHead(int x) {
        Node newNode = new Node(x);
        newNode.next = head;
        head = newNode;
    }


    /**
     * @param data
     * @param position
     */
    public void insertNth(int data, int position) {
        if (position < 0 || position > getSize()) {
            throw new RuntimeException("position less than zero or position more than the count of list");
        } else if (position == 0)
            insertHead(data);
        else {
            Node cur = head;
            Node node = new Node(data);
            for (int i = 1; i < position; ++i) {
                cur = cur.next;
            }
            node.next = cur.next;
            cur.next = node;
        }
    }

    /**
     * @return The element deleted
     */
    public void deleteHead() {
        if (isEmpty()) {
            throw new RuntimeException("The list is empty!");
        }

        head = head.next;
    }

    /**
     * @param position
     */
    public void deleteNth(int position) {
        if (position < 0 || position > getSize()) {
            throw new RuntimeException("position less than zero or position more than the count of list");
        } else if (position == 0)
            deleteHead();
        else {
            Node cur = head;
            for (int i = 1; i < position; ++i) {
                cur = cur.next;
            }
            cur.next = cur.next.next;
        }
    }

    /**
     * Checks if the list is empty
     *
     * @return true is list is empty
     */
    public boolean isEmpty() {
        return getSize() == 0;
    }

    /**
     * Prints contents of the list
     */
    public void display() {
        Node current = head;
        while (current != null) {
            System.out.print(current.value + " ");
            current = current.next;
        }
        System.out.println();
    }

    /**
     * Returns the size of the linked list
     */
    public int getSize() {
        if (head == null)
            return 0;
        else {
            Node current = head;
            int size = 1;
            while (current.next != null) {
                current = current.next;
                size++;
            }
            return size;
        }
    }

    /**
     * @param args
     */
    public static void main(String args[]) {
        LinkedList myList = new LinkedList();

        assert myList.isEmpty();

        myList.insertHead(5);
        myList.insertHead(7);
        myList.insertHead(10);

        myList.display(); // 10 -> 7 -> 5

        myList.deleteHead();

        myList.display(); // 7 -> 5

        myList.insertNth(11, 2);

        myList.display(); // 7 -> 5 -> 11

        myList.deleteNth(1);

        myList.display(); // 7-> 11

    }
}

/**
 *
 */
class Node {
    /**
     * The value of the node
     */
    int value;

    /**
     * Point to the next node
     */
    Node next;

    /**
     * Constructor
     *
     * @param value
     */
    Node(int value) {
        this.value = value;
        this.next = null;
    }
}
