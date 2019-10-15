package core.algorithm.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author maxuefeng
 */
public abstract class AbstractBinaryTree<T> implements Tree<T> {

    protected BinaryTreeNode<T> rootNode;

    private AtomicInteger index = new AtomicInteger(0);

    @Override
    public int getIndex() {
        return index.getAndIncrement();
    }

    public AbstractBinaryTree() {

    }

    public AbstractBinaryTree(BinaryTreeNode<T> rootNode) {
        this.rootNode = rootNode;
    }


    /**
     * 树结构节点定义
     *
     * @param
     */
    public static class BinaryTreeNode<T> extends Node<T> {

        public BinaryTreeNode<T> lNode;

        public BinaryTreeNode<T> rNode;

        @Override
        public String toString() {
            return "{\"node: \"[\"index\":" + index + ",\"data\"+" + data + "]}";
        }
    }


    /**
     * 生成node节点
     *
     * @param index
     * @param data
     * @return
     */
    protected static <T> BinaryTreeNode generateNode(int index, T data) {

        BinaryTreeNode binaryTreeNode = new BinaryTreeNode();

        binaryTreeNode.index = index;
        binaryTreeNode.data = data;

        return binaryTreeNode;
    }

    @Override
    public List<BinaryTreeNode> leftOrder() {

        List<BinaryTreeNode> binaryTreeNodes = new ArrayList<>();
        leftOrder(binaryTreeNodes, this.rootNode);

        return binaryTreeNodes;
    }

    private void leftOrder(List<BinaryTreeNode> binaryTreeNodes, BinaryTreeNode binaryTreeNode) {

        if (binaryTreeNode == null)
            return;

        binaryTreeNodes.add(binaryTreeNode);
        leftOrder(binaryTreeNodes, binaryTreeNode.lNode);
        leftOrder(binaryTreeNodes, binaryTreeNode.rNode);
    }

    @Override
    public List<BinaryTreeNode> midOrder() {

        List<BinaryTreeNode> binaryTreeNodes = new ArrayList<>();
        midOrder(binaryTreeNodes, this.rootNode);

        return binaryTreeNodes;
    }

    private void midOrder(List<BinaryTreeNode> binaryTreeNodes, BinaryTreeNode binaryTreeNode) {

        if (binaryTreeNode == null)
            return;

        leftOrder(binaryTreeNodes, binaryTreeNode.lNode);
        binaryTreeNodes.add(binaryTreeNode);
        leftOrder(binaryTreeNodes, binaryTreeNode.rNode);
    }

    @Override
    public List<BinaryTreeNode> rightOrder() {

        List<BinaryTreeNode> binaryTreeNodes = new ArrayList<>();
        rightOrder(binaryTreeNodes, this.rootNode);

        return binaryTreeNodes;
    }


    private void rightOrder(List<BinaryTreeNode> binaryTreeNodes, BinaryTreeNode binaryTreeNode) {

        if (binaryTreeNode == null)
            return;

        rightOrder(binaryTreeNodes, binaryTreeNode.lNode);
        rightOrder(binaryTreeNodes, binaryTreeNode.rNode);
        binaryTreeNodes.add(binaryTreeNode);
    }

    /**
     * 层序遍历  TODO  层序遍历比较难于理解  需要队列这种先进先出的数据结构辅助
     */
    public void levelTraverse() {
        levelTraverse(this.rootNode);
    }

    private void levelTraverse(BinaryTreeNode<T> root) {
        if (root == null)
            return;

        // 层序遍历时保存结点的队列
        Queue<BinaryTreeNode<T>> queue = new LinkedList<>();

        // 初始化
        queue.offer(root);

        while (!queue.isEmpty()) {

            BinaryTreeNode<T> node = queue.poll();

            // 访问节点
            System.err.print(node.data + " ");

            // 左节点不为空
            if (node.lNode != null)
                queue.offer(node.lNode);

            // 右节点不为空
            if (node.rNode != null)
                queue.offer(node.rNode);
        }
    }
}
