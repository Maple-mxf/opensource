package io.jopen.core.algorithm.sort;

/**
 * 实现快速排序算法
 * 1. 在数组中选一个基准数（通常为数组第一个）；
 * 2. 将数组中小于基准数的数据移到基准数左边，大于基准数的移到右边；
 * 3. 对于基准数左、右两边的数组，不断重复以上两个过程，{直到每个子集只有一个元素，即为全部有序。}
 * 4. 逐步递归  递归的最终条件是只有两个元素
 * <p>
 * {@link java.util.Arrays#sort(int[])}
 * <p>
 * <p>
 * TODO
 *
 * @author maxuefeng
 * @since 2020/1/6
 */
public class SequentiallySort {

    /**
     * @param array 目标排序数组
     */
    public static void compute(int[] array) {
        assert array.length > 1;
        int benchmark = array[0], leftSentry = array[0], rightSentry = array[array.length - 1];
        int leftPoint = 0, rightPoint = array.length;


    }


    public static void compute(int array, int left, int right) {
    }

}
