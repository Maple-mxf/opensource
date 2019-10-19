package io.jopen.core.algorithm.tree;

import com.google.common.base.Joiner;

/**
 * @author maxuefeng
 * @since 2019/10/19
 */
public class BinaryTree {

    // 访问方式
    // 树的高度
    static class Node {

        Object data;
        Integer index;
    }

    // left
    public Node leftNode;

    // right
    public Node rightNode;


    /**Javadoc
     * @return
     * @see com.google.common.base.Joiner#equals(Object)
     * <p>
     * {@link Joiner#clone()}
     */
    public static BinaryTree genenrateGenericTree() {
        // 完全二叉树  满二叉树
        BinaryTree binaryTree = new BinaryTree();
        // binaryTree.leftNode = new Node();
        // binaryTree.rightNode = new Node();

        //
        return binaryTree;
    }
}
