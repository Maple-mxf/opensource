package core.algorithm.common;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * 关于String的一系列算法工具类
 *
 * @author maxuefeng
 */
public class StringAlgorithm {

    /**
     * 给定一个字符串，输出不含有重复字符的最长子串的长度。
     * 例如： 输入: "abcabcbb"  输出: 3； 输入："aaaaa" 输出：1
     *
     * @param origin 原始字符串
     * @return 不含有重复字符长子串的长度
     */
    public int getNoRepeatLength(String origin) {

        if (StringUtils.isBlank(origin)) return 0;

        StringBuilder currentStr = new StringBuilder();

        List<String> vars = Lists.newArrayList();

        for (int i = 0; i < origin.length(); i++) {

            char currentChar = origin.charAt(i);

            if (!currentStr.toString().contains(String.valueOf(currentChar))) {
                currentStr.append(currentChar);
            } else {

                vars.add(currentStr.toString());

                currentStr = new StringBuilder(String.valueOf(currentChar));
            }
        }

        Optional<String> op = vars.parallelStream().max((o1, o2) -> o1.length() - o2.length() > 0 ? 1 : 0);

        return op.map(String::length).orElse(0);
    }


    /**
     * 最长回文串
     * 给定一个包含大写字母和小写字母的字符串，找到通过这些字母构造成的最长的回文串。
     * 在构造过程中，请注意区分大小写。比如 "Aa" 不能当做一个回文字符串。
     * <p>
     * 输入: "abccccdd"
     * 输出: 7
     * <p>
     * 我们可以构造的最长的回文串是"dccaccd", 它的长度是 7
     * <p>
     * 算法思想   左面一个指针left=0  右面一个指针right=length-1
     * 左面的指针向右移动   右面的指针向左移动  直到两个指针撞上
     */
    public static int substringBackTextDoublePoint(String origin) {

        long start = System.currentTimeMillis();

        if (StringUtils.isBlank(origin)) return 0;

        // 如果原始字符是回文字符串
        if (isBackToText(origin)) return origin.length();

        Set<String> substrings = new TreeSet<>();

        // 嵌套两层循环
        for (int i = 0; i < origin.length() - 1; i++) {

            for (int j = origin.length() - 1; j > i; j--) {

                if (origin.charAt(i) == origin.charAt(j) && isBackToText(origin.substring(i, j + 1))) {
                    // 如果是回文字符串
                    // 则加入Set集合  TODO  待优化
                    substrings.add(origin.substring(i, j + 1));
                }
            }
        }

        Optional<String> op = substrings.parallelStream().max(Comparator.comparingInt(String::length));

        long end = System.currentTimeMillis();

        System.err.println(end - start);

        return op.map(String::length).orElse(0);
    }

    /**
     * 基于上面的算法优化之后
     *
     * @param origin
     * @return
     */
    public static int substringBackTextLength2(String origin) {

        long start = System.currentTimeMillis();

        if (StringUtils.isBlank(origin)) return 0;

        // 如果原始字符是回文字符串
        if (isBackToText(origin)) return origin.length();

        Set<String> substrings = new TreeSet<>();

        // 嵌套两层循环
        for (int i = 0; i < origin.length() - 1; i++) {

//            int tmpIndex = origin.lastIndexOf(origin.charAt())
            for (int j = origin.length() - 1; j > i; j--) {

                if (origin.charAt(i) == origin.charAt(j) && isBackToText(origin.substring(i, j + 1))) {
                    // 如果是回文字符串
                    // 则加入Set集合  TODO  待优化
                    substrings.add(origin.substring(i, j + 1));
                }
            }
        }

        Optional<String> op = substrings.parallelStream().max(Comparator.comparingInt(String::length));

        long end = System.currentTimeMillis();

        System.err.println(end - start);

        return op.map(String::length).orElse(0);
    }


    /**
     * 判断回文--单指针
     *
     * @param origin
     * @return
     */
    public static int substringBackTextSinglePoint(String origin) {

        long start = System.currentTimeMillis();

        int length = 0;

        if (StringUtils.isBlank(origin)) return length;

        char[] chars = new char[origin.length()];

        int pointer1 = -1;
        int pointer2 = -1;

        // 默认偶数
        boolean oddNumber = false;
        boolean oddNumber2 = false;

        for (int i = 0; i < origin.length(); i++) {

            chars[i] = origin.charAt(i);

            if (pointer1 != -1) {

                // 奇数
                if (oddNumber) {

                    //判断是否越界,如果是
                    if ((2 * pointer1 - i) < 0) {

                        //判断和之前所存的length比较
                        length = length > i ? length : i;
                        pointer1 = -1;
                        oddNumber = false;

                    }

                    // 到达回文边界
                    if (pointer1 != -1 && chars[2 * pointer1 - i] != origin.charAt(i)) {

                        length = length > ((i - 2) - (2 * pointer1 - i)) ? length : ((i - 2) - (2 * pointer1 - i));
                        pointer1 = -1;
                        oddNumber = false;
                    }

                }
                // 偶数
                else {

                    // 判断是否越界,如果是
                    if ((2 * pointer1 + 1 - i) < 0) {

                        //判断和之前所存的length比较
                        length = length > i ? length : i;
                        pointer1 = -1;
                        oddNumber = false;

                    }

                    // 到达回文边界
                    if (pointer1 != -1 && chars[2 * pointer1 + 1 - i] != origin.charAt(i)) {
                        length = length > (2 * (i - pointer1)) ? length : (2 * (i - pointer1));
                        pointer1 = -1;
                        oddNumber = false;
                    }
                }
            } else {

                if (i - 1 >= 0 && chars[i] == chars[i - 1]) {
                    pointer1 = i - 1;
                    oddNumber = false;
                }

                // 偶数
                else if (i - 2 >= 0 && chars[i] == chars[i - 2]) {

                    pointer1 = i - 1;

                    //奇数
                    oddNumber = true;
                }
            }

            if (pointer2 != -1) {

                // 奇数
                if (oddNumber2) {

                    // 判断是否越界,如果是
                    if ((2 * pointer2 - i) < 0) {

                        // 判断和之前所存的length比较
                        length = length > i ? length : i;
                        pointer2 = -1;
                        oddNumber2 = false;
                        System.out.println();
                    }

                    // 到达回文边界
                    if (pointer2 != -1 && chars[2 * pointer2 - i] != origin.charAt(i)) {
                        length = length > ((i - 2) - (2 * pointer2 - i)) ? length : ((i - 2) - (2 * pointer2 - i));
                        pointer2 = -1;
                        oddNumber2 = false;
                    }
                } else {
                    //判断是否越界,如果是
                    if ((2 * pointer2 + 1 - i) < 0) {

                        // 判断和之前所存的length比较
                        length = length > i ? length : i;
                        pointer2 = -1;
                        oddNumber2 = false;
                    }

                    // 到达回文边界
                    if (pointer2 != -1 && chars[2 * pointer2 + 1 - i] != origin.charAt(i)) {
                        length = length > (2 * (i - pointer2 - 1)) ? length : (2 * (i - pointer2 - 1));
                        pointer2 = -1;
                        oddNumber2 = false;
                    }
                }
            } else if (pointer1 != -1 || length > 0) {

                if (chars[i] == chars[i - 1]) {
                    pointer2 = pointer1 != (i - 1) ? i - 1 : -1;

                    // 偶数
                    oddNumber2 = false;

                } else if (i - 2 >= 0 && chars[i] == chars[i - 2]) {

                    pointer2 = pointer1 != (i - 1) ? i - 1 : -1;

                    // 奇数
                    oddNumber2 = true;
                }
            }
        }

        long end = System.currentTimeMillis();

        System.out.println("耗时: " + (end - start));
        return length;
    }


    /**
     * 判断一个字符串是否回文字符串
     *
     * @param origin
     * @return
     */
    public static boolean isBackToText(String origin) {

        int left = 0;
        int right = origin.length() - 1;

        boolean isBackToText = true;

        // 是否是奇数
        boolean oddNumber = !(origin.length() % 2 == 0);

        while (!(oddNumber ? (left == right) : ((left + 1) == right) && origin.substring(0, left + 1).equals(origin.substring(right)))) {

            if (origin.charAt(left) != origin.charAt(right)) {
                isBackToText = false;
                break;
            }

            left++;
            right--;
        }

        return isBackToText;
    }


    /**
     * https://blog.csdn.net/v_july_v/article/details/7041827
     * * 有一个文本串S，和一个模式串P，现在要查找P在S中的位置
     *
     * @param origin
     * @param template
     * @return location
     */
    public static int kmp(String origin, String template) {
        return 0;
    }


    /**
     * 最长公共前缀 编写一个函数来查找字符串数组中的最长公共前缀。如果不存在公共前缀，返回空字符串 ""。
     */

    public static String getCommonPrefix(String[] origin) {

        if (origin == null) return null;


        return "";
    }


    /**
     * 打印九九乘法表
     * 1 * 1  = 1;
     * 2 * 1  = 2;   2 * 2  = 4;
     * 3 * 1  = 3;   3 * 2  = 6;   3 * 3  = 9;
     * 4 * 1  = 4;   4 * 2  = 8;   4 * 3  = 12;   4 * 4  = 16;
     * 5 * 1  = 5;   5 * 2  = 10;   5 * 3  = 15;   5 * 4  = 20;   5 * 5  = 25;
     * 6 * 1  = 6;   6 * 2  = 12;   6 * 3  = 18;   6 * 4  = 24;   6 * 5  = 30;   6 * 6  = 36;
     * 7 * 1  = 7;   7 * 2  = 14;   7 * 3  = 21;   7 * 4  = 28;   7 * 5  = 35;   7 * 6  = 42;   7 * 7  = 49;
     * 8 * 1  = 8;   8 * 2  = 16;   8 * 3  = 24;   8 * 4  = 32;   8 * 5  = 40;   8 * 6  = 48;   8 * 7  = 56;   8 * 8  = 64;
     * 9 * 1  = 9;   9 * 2  = 18;   9 * 3  = 27;   9 * 4  = 36;   9 * 5  = 45;   9 * 6  = 54;   9 * 7  = 63;   9 * 8  = 72;   9 * 9  = 81;
     *
     * @return
     */
    public static String multiplicationTable(Integer limit) {

        if (limit == null) limit = 9;

        StringBuilder rs = new StringBuilder();

        for (int i = 1; i <= limit; i++) {

            for (int j = 1; j <= i; j++) {
                rs.append(i).append(" * ").append(j).append("  = ").append(i * j).append(";   ");
            }

            rs.append("\n");

        }
        return rs.toString();
    }

    public String multiplicationTable() {
        return multiplicationTable(null);
    }


}

















