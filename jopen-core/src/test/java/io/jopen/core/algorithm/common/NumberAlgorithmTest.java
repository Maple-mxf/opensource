package io.jopen.core.algorithm.common;

import org.junit.Test;

import java.util.List;

/**
 * @author maxuefeng
 */
public class NumberAlgorithmTest {

    /**
     * 测试某一个区间的质数
     */
    @Test
    public void testGetPrimeNumbers() {

        List<Integer> primeNumbers = NumberAlgorithm.getPrimeNumbers(1, 1000);

        for (Integer primeNumber : primeNumbers) {
            System.out.println(primeNumber);
        }
    }


    /**
     * 测试某一个区间的质数的和
     */
    @Test
    public void testGetPrimeNumberSum() {
        System.out.println(NumberAlgorithm.getPrimeNumberSum(1, 100));
    }

}
