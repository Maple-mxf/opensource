package io.jopen.core.algorithm.tree;

import java.util.Comparator;

/**
 * 特殊的二叉树：搜索二叉树
 * 二叉搜索树的特性：
 * 1 若左子树非空，则左子树所有结点关键字值均小于根结点关键字的值
 * 2 若右子树非空，则右子树所有结点关键字值均小于根结点关键字的值
 * 3 左，右子树本身也是一个二叉搜索树（BST）
 * <p>
 * 给定任意两个节点，BST(Binary Search Tree) 必须能够判断这两个节点的值是小于、大于还是等于。
 * [http://www.cnblogs.com/gaochundong/p/binary_search_tree.html]
 *
 * @author maxuefeng
 */
public class SearchBinaryTree<T extends Comparator<T>> extends BinaryTree<T> {

    public SearchBinaryTree(BinaryTreeNode<T> rootNode) {
        super(rootNode);
    }

    public SearchBinaryTree() {
    }


    private void check(BinaryTreeNode<T> node) {

        if (node == null) return;

        if (node.lNode != null) {

            // node节点的数据要存在可以比较的性质
            int rs = node.data.compare(node.data, node.lNode.data);

            if (rs > 0) {
                throw new RuntimeException("Build Search Binary Tree Exception");
            }
        }
    }


    /**
     * 删除元素   删除元素之后依然要保持BST的结构
     *
     * @param node Node元素的值
     */
    public boolean remove(BinaryTreeNode<T> node) {
        return false;
    }

    /**
     * 删除元素   删除元素之后依然要保持BST的结构
     *
     * @param index Node节点的索引值
     */
    public boolean remove(int index) {
        return false;
    }

    /**
     * 添加元素   添加元素之后依然要保持BST的结构
     *
     * @param node
     */
    public void add(BinaryTreeNode<T> node) {
        if (this.rootNode == null) {
            this.rootNode = node;
        }
    }


    /**
     * 重构当前的树的结构
     */
    private void refactor() {
        refactor(this.rootNode);
    }

    /**
     * 重构的树的结构
     *
     * @param node
     */
    private void refactor(BinaryTreeNode<T> node) {

        // 如果当前节点为空  return
        if (node == null) return;

        // 递归左子树
        refactor(node.lNode);

        // 递归右子树
        refactor(node.rNode);

    }


}
