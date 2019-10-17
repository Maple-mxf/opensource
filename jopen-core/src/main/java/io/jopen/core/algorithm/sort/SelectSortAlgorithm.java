package io.jopen.core.algorithm.sort;

/**
 * 选择排序
 *
 * @author maxuefeng
 */
public class SelectSortAlgorithm implements SortAlgorithm {


    /**
     * 选择排序  给定数组：int[] origin={里面n个数据}；
     * 第1趟排序，在待排序数据arr[1]~origin[n]中选出最小的数据，将它与arrr[1]交换；
     * 第2趟，在待排序数据arr[2]~origin[n]中选出最小的数据，将它与r[2]交换；
     * 以此类推，第i趟在待排序数据arr[i]~origin[n]中选出最小的数据，将它与r[i]交换，直到全部排序完成。
     *
     * @param origin 原始数组
     * @return 返回排好序的数据
     */
    @Override
    public int[] sort(int[] origin) {
        if (origin.length == 0) return origin;

        for (int i = 0; i < origin.length; i++) {
            // 选出剩下元素中的最小元素
            int minIndex = i;

            for (int j = minIndex + 1; j < origin.length; j++) {
                if (origin[minIndex] > origin[j]) {
                    minIndex = j;
                }
            }

            if (origin[minIndex] != origin[i]) {
                int temp = origin[minIndex];
                origin[minIndex] = origin[i];
                origin[i] = temp;
            }
        }
        return origin;
    }

}
