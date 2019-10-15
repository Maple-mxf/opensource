package core.algorithm.tree;

/**
 * 二叉树
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
 *
 * @author maxuefeng
 */
public class BinaryTree<T> extends AbstractBinaryTree<T> {

    public BinaryTree(BinaryTreeNode<T> rootBinaryTreeNode) {
        super(rootBinaryTreeNode);
    }

    public BinaryTree() {

    }


    public BinaryTreeNode getRootNode() {
        return this.rootNode;
    }

    /**
     * TODO  二叉树构建[自定义构建的二叉树]  构建操作位于外层  此处只是举个例子
     */
    @Deprecated
    public void createBinaryTree() {

        BinaryTreeNode B = AbstractBinaryTree.generateNode(1, "B");
        BinaryTreeNode C = AbstractBinaryTree.generateNode(2, "C");
        BinaryTreeNode D = AbstractBinaryTree.generateNode(3, "D");
        BinaryTreeNode E = AbstractBinaryTree.generateNode(4, "E");
        BinaryTreeNode F = AbstractBinaryTree.generateNode(5, "F");
        BinaryTreeNode G = AbstractBinaryTree.generateNode(6, "G");

        // 从底层向上构建
        B.lNode = C;
        B.rNode = D;
        E.lNode = F;
        E.rNode = G;

        // 构建root顶点的子树
        rootNode.lNode = B;
        rootNode.rNode = E;
    }


    /**
     * @return 树的高度
     */
    @Override
    public int height() {

        return height(this.rootNode);
    }

    private int height(BinaryTreeNode node) {
        if (node == null) return 0;

        int lHeight = height(node.lNode);
        int rHeight = height(node.rNode);

        return lHeight > rHeight ? lHeight + 1 : rHeight + 1;
    }


    /**
     * 获取左子树的高度
     *
     * @param node
     * @return
     */
    protected int leftNodeHeight(BinaryTreeNode node) {
        if (node == null) return 0;
        int height = leftNodeHeight(node.lNode);
        return height + 1;
    }

    /**
     * 获取右子树的高度
     *
     * @param node
     * @return
     */
    protected int rightNodeHeight(BinaryTreeNode node) {
        if (node == null) return 0;
        int height = rightNodeHeight(node.rNode);
        return height + 1;
    }


    /**
     * 获取当前树的size
     *
     * @return
     */
    @Override
    public int size() {

        return size(this.rootNode);
    }

    private int size(BinaryTreeNode binaryTreeNode) {
        if (binaryTreeNode == null) return 0;

        int lSize = size(binaryTreeNode.lNode);
        int rSize = size(binaryTreeNode.rNode);

        return lSize + rSize + 1;
    }

}
