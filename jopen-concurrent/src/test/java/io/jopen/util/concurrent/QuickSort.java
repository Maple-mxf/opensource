package io.jopen.util.concurrent;

/**
 * {@link java.util.Arrays#sort(int[])}
 *
 * @author maxuefeng
 * @since 2020-01-07
 */
public class QuickSort {

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void sort(int[] array, int fromIndex, int toIndex) {
        if (toIndex > fromIndex) {
            int partition = partition(array, fromIndex, toIndex);
            sort(array, fromIndex, partition - 1);
            sort(array, partition + 1, toIndex);
        }
    }

    /**
     * 双指针移动查询
     *
     * @param array
     * @param fromIndex
     * @param toIndex
     * @return
     */
    public static int partition(int[] array, int fromIndex, int toIndex) {
        int base = array[fromIndex], i = fromIndex, j = toIndex;
        while (i < j) {
            // 从右向左找一个比base小的
            while (i < j && array[j] >= base) --j;
            // 从左向右找一个比base大的
            while (i < j && array[i] <= base) ++i;
            if (i != j) swap(array, i, j);
        }
        if (i != fromIndex) swap(array, i, fromIndex);
        return i;
    }
}
