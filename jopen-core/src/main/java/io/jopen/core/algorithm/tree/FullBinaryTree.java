package io.jopen.core.algorithm.tree;

/**
 * 满二叉树
 * 国内定义：一个二叉树，如果每一个层的结点数都达到最大值，则这个二叉树就是满二叉树。
 * 也就是说，如果一个二叉树的层数为K，且结点总数是(2^k) -1 ，则它就是满二叉树。
 * 国外定义：满二叉树的结点要么是叶子结点，度为0，要么是度为2的结点，不存在度为1的结点
 *
 * @author maxuefeng
 */
public class FullBinaryTree<T> extends CompleteBinaryTree<T> {


    public FullBinaryTree(BinaryTreeNode<T> node) {
        super(node);

        // 不是满二叉树
        if (!isFullBinaryTree())
            throw new RuntimeException("this node is not FullBinaryTree");
    }


    public FullBinaryTree() {
    }


}
