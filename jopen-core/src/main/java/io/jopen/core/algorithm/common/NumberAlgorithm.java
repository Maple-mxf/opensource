package io.jopen.core.algorithm.common;

import java.util.ArrayList;
import java.util.List;

/**
 * @author maxuefeng
 */
public class NumberAlgorithm {


    /**
     * 获取某一个区间的质数
     * <p>
     * 质数：只能被1和自身整除的数（1和2都属于质数）
     *
     * @param start
     * @param end
     * @return
     */
    public static List<Integer> getPrimeNumbers(int start, int end) {

        List<Integer> primeNumbers = new ArrayList<>();

        for (; start < end; start++) {

            if (start == 1 || start == 2) continue;

            int j = 2;

            // 是否被整除
            boolean divisible = false;

            while (j < start) {

                if (start % j == 0) {
                    // 不是质数
                    divisible = true;
                    break;
                }
                j++;
            }

            if (!divisible)
                primeNumbers.add(start);
        }
        return primeNumbers;
    }

    /**
     * 获取某一个区间的质数的和
     *
     * @param start
     * @param end
     * @return
     */
    public static Integer getPrimeNumberSum(int start, int end) {

        int rs = 0;

        for (Integer p : getPrimeNumbers(start, end)) {
            rs += p;
        }

        return rs;
    }


    /**
     * 输入一个矩阵  顺时针打印每个数字
     *
     * @param num
     * @param start
     * @param end
     */
    public static void output(int[][] num, int start, int end) {
        if (start >= end || end <= 0) return;
        for (int i = start; i <= end; i++) {
            System.out.println(num[start][i]);
        }
        for (int i = start + 1; i <= end; i++) {
            System.out.println(num[i][end]);
        }
        for (int i = end - 1; i >= start; i--) {
            System.out.println(num[end][i]);
        }
        for (int i = end - 1; i > start; i--) {
            System.out.println(num[i][start]);
        }
        output(num, start + 1, end - 1);
    }


}
