package io.jopen.core.algorithm.common;

import org.junit.Test;

/**
 * @author maxuefeng
 */
public class StringAlgorithmTest {

    /**
     * 测试一个字符串是否是回文字符串
     */
    @Test
    public void testIsBackToText() {

        String tmp = "abcacba";
        
        boolean backToText = StringAlgorithm.isBackToText(tmp);

        System.err.println(backToText);
    }


    /**
     * 测试最长的回文字符串长度
     * <p>
     * 算法在没有优化之前耗时84毫秒
     * 算法在优化之后耗时
     */
    @Test
    public void testSubstringBackTextLength() {

        String tmp = "abcacbannnnn";
        int length = StringAlgorithm.substringBackTextDoublePoint(tmp);

        System.out.println(length);
    }

}
