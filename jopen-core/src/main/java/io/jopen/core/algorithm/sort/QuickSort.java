package io.jopen.core.algorithm.sort;

/**
 * {@link java.util.Arrays#sort(int[])}
 * 1、先从数列中取出一个数作为基准数
 * 2、分区过程，将比这个数大的数全放到它的右边，小于或等于它的数全放到它的左边
 * 3、再对左右区间重复第二步，直到各区间只有一个数
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

    public static void main(String[] args) {
        int[] array = new int[]{1, 4, 2, 3, 6};
        sort(array, 0, 4);
        for (int i : array) {
            System.err.print(i);
        }
    }


}
