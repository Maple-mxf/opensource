package io.jopen.core.algorithm.sort;

/**
 * 冒泡排序
 *
 * @author maxuefeng
 */
public class BubbleSortAlgorithm implements SortAlgorithm {


    /**
     * 冒泡排序  相邻的数据表达大小  如果满足条件则交换位置
     *
     * @param origin 原始数组
     * @return 从小到大排序的数组
     */
    @Override
    public int[] sort(int[] origin) {

        if (origin.length == 0) return origin;

        for (int i = 0; i < origin.length; i++) {
            //
            for (int j = 0; j < i; j++) {

                // 数组元素交换位置
                if (origin[i] < origin[j]) {
                    int temp = origin[i];
                    origin[i] = origin[j];
                    origin[j] = temp;
                }
            }
        }
        return origin;
    }
}
