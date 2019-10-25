package io.jopen.leopard.base.storage;

import io.jopen.leopard.base.annotation.PrimaryKey;

import java.io.Serializable;

/**
 * @author maxuefeng
 * @since 2019/10/22
 */
public class Student implements Serializable {

    @PrimaryKey
    private String id;
    private String name;
    private Integer age;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
