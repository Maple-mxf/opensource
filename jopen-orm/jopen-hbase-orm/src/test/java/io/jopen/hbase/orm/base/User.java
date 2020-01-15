package io.jopen.hbase.orm.base;

/**
 * @author maxuefeng
 * @since 2020/1/15
 */
public class User {

    String name;
    int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
