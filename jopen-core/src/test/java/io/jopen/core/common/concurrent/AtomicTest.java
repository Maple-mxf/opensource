package io.jopen.core.common.concurrent;

/**
 * 原子性测试
 *
 * @author maxuefeng
 * @since 2019/9/6
 */
public class AtomicTest {

    public void simpleTest1() {
        int a = 10;
        ++a;
        int b = a;
    }
}
