package io.jopen.core.algorithm.tree;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

/**
 * 完全二叉树
 * 若设二叉树的深度为h，除第 h 层外，其它各层 (1～h-1) 的结点数都达到最大个数，
 * 第 h 层所有的结点都连续集中在最左边，这就是完全二叉树。
 * <p>
 * <p>
 * 节点的度：节点的子节点的数量为该节点的度。在完全二叉树中，节点的度最多为2，最少为0。
 * 叶子节点：度为0的节点称为叶子节点。
 * 二叉树的基本性质
 * 假设完全二叉树的总结点树为n，度为0的节点数量为n0，度为1的节点数量为n1，度为2的节点数量为n2,则显然满足n=n0+n1+n2。
 * <p>
 * 性质1:二叉树第i层上的结点数目最多为2^(i-1),(i≥1)。
 * <p>
 * 性质2:深度为k的二叉树至多有(2^k)-1个结点(k≥1)。 n=2^0+2^1+…+2^(k-1)=(2^k)-1故命题正确。
 * <p>
 * 性质3 在任意-棵二叉树中，若终端结点的个数为n0，度为2的结点数为n2，则no=n2+1。
 * <p>
 * 完全二叉树的性质
 * 性质1： 当n为偶数时，n1=1，n为奇数时，n1=0。
 * <p>
 * TODO  满二叉树是完全二叉树的特例
 * <p>
 * 依据二叉树的性质，完全二叉树和满二叉树采用顺序存储比较合适
 * <p>
 * <p>
 * 完全二叉树应用：【https://juejin.im/post/5a324c08f265da431f4b18ac】
 * <p>
 *
 * @author maxuefeng
 */
public class CompleteBinaryTree<T> extends BinaryTree<T> {


    /**
     * 对于一棵有n个结点的完全二叉树，对其结点按层序编号，从上到下，从左到右，对任一结点i (1 = i <= n)有：
     * <p>
     * 如果i = 1，则结点i是二叉树的根，无父结点；如果i > 1，则其父结点的位置是⌊i / 2⌋（向下取整）。
     * 如果2i > n，则结点i无左孩子（结点i为叶子结点）；否则其左孩子是结点2i。
     * 如果2i + 1 > n，则结点i无右孩子；否则其右孩子是结点2i + 1。
     *
     * @param node
     */
    public CompleteBinaryTree(BinaryTreeNode<T> node) {
        super(node);

        if (isCompleteBinaryTree(node))
            throw new RuntimeException("this node is not a BinaryTreeNode");
    }

    public CompleteBinaryTree() {
    }

    /**
     * 判断一个属是否是满二叉树
     *
     * @return
     */
    protected boolean isFullBinaryTree() {

        // 根据奇数偶数判断当前树是否是一个满二叉树
        int size = this.size();

        int height = this.height();

        // 如果一个二叉树的层数为K，且结点总数是(2^k) -1 ，则它就是满二叉树
        return (Math.pow(2, height) - 1) == size;
    }

    /**
     * 判断一棵树是否为完全二叉树
     * <p>
     * 若设二叉树的深度为h，除第 h 层外，其它各层 (1~h-1) 的结点数都达到最大个数，第 h 层所有的结点都连续集中在最左边，这就是完全二叉树
     * <p>
     * 1 当一个结点有右孩子，但是没有左孩子，直接返回false
     * 2 当一个节点有左孩子无右孩子，那么接下来要遍历的节点必须是叶子结点。（叶子结点左右孩子为空）
     * [https://blog.csdn.net/FreeeLinux/article/details/53735417]
     * <p>
     * 剩余队列判空法同样的用到了队列这种辅助结构，那仫如何只通过队列这个结构来
     * 判断一棵树是否是完全二叉树呢？试想一下，如果我们把一棵树的所有结点，
     * 包括叶子结点的左右空孩子都通过层序遍历入队列会发生什仫情况？
     * <p>
     * TODO  以下算法不易理解  可到[https://blog.csdn.net/number_0_0/article/details/76177479]参考二叉树的层级遍历算法
     *
     * @param node
     */
    private boolean isCompleteBinaryTree(BinaryTreeNode<T> node) {

        // 当前节点是一个叶子节点
        if (checkLeafNode(node)) {
            return false;
        }

        Queue<BinaryTreeNode<T>> queue = new LinkedList<>();

        // 初始化数据
        queue.offer(this.rootNode);

        BinaryTreeNode<T> tmpNode = queue.poll();

        while (tmpNode != null) {

            // 访问节点数据
            System.err.println(tmpNode.index);
            System.err.println(tmpNode.data);

            queue.offer(tmpNode.lNode);

            queue.offer(tmpNode.rNode);

            tmpNode = queue.poll();
        }

        // 得到的queue一定是所有元素为null

        boolean completeBinaryTree = true;

        while (!queue.isEmpty()) {

            if (queue.poll() != null) {
                completeBinaryTree = false;
                break;
            }
        }
        
        return completeBinaryTree;

    }

    /**
     * 检查原子树
     *
     * @param node
     * @return
     */
    @Deprecated
    private boolean checkAtomicTreeNode(BinaryTreeNode<T> node) {

        Objects.requireNonNull(node);

        // 当一个结点有右孩子，但是没有左孩子，直接返回false
        if (node.rNode != null && node.lNode == null)
            return false;

        // 当一个节点有左孩子无右孩子，那么接下来要遍历的节点必须是叶子结点
        if (node.lNode != null && node.rNode == null) {

            BinaryTreeNode<T> leafNode = node.lNode;
            // 左子树必须是个叶子节点
            return leafNode.lNode == null && leafNode.rNode == null;
        }

        return true;
    }

    /**
     * 检查一棵树是否为叶子节点
     *
     * @param node
     * @return
     */
    private boolean checkLeafNode(BinaryTreeNode<T> node) {
        // 左子树必须是个叶子节点
        return node.lNode == null && node.rNode == null;
    }
}






















