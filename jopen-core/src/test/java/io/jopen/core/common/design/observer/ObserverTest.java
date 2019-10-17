package io.jopen.core.common.design.observer;

import org.junit.Test;

/**
 * 测试观察者模式代码
 *
 * @author maxuefeng
 */
public class ObserverTest {

    /**
     * result:
     * 我是读者A,收到了新书:Book [bookName=朝花夕拾, author=鲁迅]
     * 我是读者B,收到了新书:Book [bookName=朝花夕拾, author=鲁迅]
     */
    @Test
    public void simpleTest() {

        Library lib = new Library();

        // 添加
        lib.addObserver(new ReadorA());
        lib.addObserver(new ReadorB());

        Book book = new Book("朝花夕拾", "鲁迅");
        lib.addBook(book);
    }
}
