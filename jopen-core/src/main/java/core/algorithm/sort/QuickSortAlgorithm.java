package core.algorithm.sort;

/**
 * 快速排序
 *
 * @author maxuefeng
 */
public class QuickSortAlgorithm implements SortAlgorithm {

    private int[] origin;

    public QuickSortAlgorithm(int[] origin) {
        this.origin = origin;
    }

    /**
     * 快速排序
     * 1: 在待排序的元素任取一个元素作为基准(通常选第一个元素，但最的选择方法是从待排序元素中随机选取一个作为基准)，称为基准元素
     * 2: 将待排序的元素进行分区，比基准元素大的元素放在它的右边，比其小的放在它的左边
     * 3: 对左右两个分区重复以上步骤直到所有元素都是有序的
     * <p>
     * 在初始状态下，数字6在序列的第1位。我们的目标是将6挪到序列中间的某个位置，假设这个位置是k
     * 现在就需要寻找这个k，并且以第k位为分界点，左边的数都小于等于6，右边的数都大于等于6
     * </p>
     *
     * @return 返回有序数组
     */
    public int[] sort(int left, int right) {
        if (left > right)
            return this.origin;
        // 基数                左哨兵     右哨兵     记录最右侧的变量
        int i = left, j = right, temp = origin[left];

        while (i != j) {

            // 先从右侧开始遍历
            while (origin[j] > temp && j > i) j--;

            // 左侧哨兵开始移动
            while (origin[i] < temp && j > i) i++;

            // 交换变量
            if (i < j) {
                int tmp = origin[j];
                origin[j] = origin[i];
                origin[i] = origin[j];
            }
        }

        // 到此第一遍变量交换完毕
        int[] leftArray = new int[i - 1];

        System.arraycopy(origin, 0, leftArray, 0, i);

        int[] rightArray = new int[right - j];

        int t = 0;
        for (int k = i + 1; k < right; k++) {
            rightArray[t] = origin[k];
            ++t;
        }

        sort(left, i - 1);// 继续处理左边的

        sort(i + 1, right);// 继续处理右边的

        return origin;
    }

    @Override
    public int[] sort(int[] origin) {
        return sort(0, origin.length);
    }
}
