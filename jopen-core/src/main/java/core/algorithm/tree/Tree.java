package core.algorithm.tree;

import java.io.Serializable;
import java.util.List;

/**
 * @author maxuefeng
 */
public interface Tree<T> extends Serializable {

    /**
     *
     * @return
     */
    int getIndex();

    /**
     * 获取当前树的高度
     *
     * @return
     */
    int height();

    /**
     * 获取树的size
     *
     * @return
     */
    int size();

    /**
     * 先序遍历
     *
     * @return
     */
    List<AbstractBinaryTree.BinaryTreeNode> leftOrder();

    /**
     * 中序遍历
     *
     * @return
     */
    List<AbstractBinaryTree.BinaryTreeNode> midOrder();

    /**
     * 后序遍历
     *
     * @return
     */
    List<AbstractBinaryTree.BinaryTreeNode> rightOrder();

    /**
     * 抽象的Node节点
     *
     * @param <T>
     */
    class Node<T> implements Serializable {

        /**
         * index属性可以为空吗？  index表示的是当前元素的唯一标识符
         */
        protected int index;

        protected T data;
    }


}
