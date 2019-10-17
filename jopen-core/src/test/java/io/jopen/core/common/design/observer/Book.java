package io.jopen.core.common.design.observer;

/**
 * @author maxuefeng
 */
public class Book {

    // 书名
    private String bookName;

    // 作者
    private String author;

    public Book(String bookName, String author) {
        this.bookName = bookName;
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book [bookName=" + bookName + ", author=" + author + "]";
    }
}
