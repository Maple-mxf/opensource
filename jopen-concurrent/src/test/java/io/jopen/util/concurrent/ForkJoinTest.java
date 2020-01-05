package io.jopen.util.concurrent;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

/**
 * {@link java.util.concurrent.BlockingQueue}
 * {@link java.util.concurrent.TransferQueue}
 * {@link java.util.concurrent.ForkJoinTask}
 * {@link java.util.concurrent.RecursiveAction} 递归的触发动作  具体请参考分而治之算法
 * {@link RecursiveTask}
 *
 * @author maxuefeng
 * @since 2020/1/5
 */
public class ForkJoinTest {

    class SortTask extends RecursiveAction {

        // 目标排序数组
        final int[] array;
        final int startIndex;
        final int endIndex;

        // 每个小数组的length
        final int THRESHOLD = 30;

        SortTask(int[] array) {
            this.array = array;
            this.startIndex = 0;
            this.endIndex = array.length - 1;
        }

        SortTask(int[] array, int startIndex, int endIndex) {
            this.array = array;
            this.startIndex = startIndex;
            this.endIndex = endIndex;
        }

        @Override
        protected void compute() {
        }

        int partition(int[] array, int startIndex, int endIndex) {
            return 0;
        }

        void sort(int[] array, int lo, int hi) {
            Arrays.sort(array, lo, hi + 1);
        }
    }
}
