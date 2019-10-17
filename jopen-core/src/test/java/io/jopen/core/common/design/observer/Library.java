package io.jopen.core.common.design.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 被观察者
 *
 * @author maxuefeng
 */
public class Library extends Observable {


    // 使用list用于存放图书
    private List<Book> bookList;

    public Library() {

        this.bookList = new ArrayList<>();

        // 添加两本书
        Book android = new Book("Android", "李江东");
        Book HongLou = new Book("红楼梦", "曹雪芹");

        this.bookList.add(android);
        this.bookList.add(HongLou);
    }

    public void addBook(Book book) {

        this.bookList.add(book);

        // 被观察者更新时通知观察者
        super.notifyObservers(book);
    }

    public void delBook(Book book) {
        this.bookList.remove(book);
    }
}
