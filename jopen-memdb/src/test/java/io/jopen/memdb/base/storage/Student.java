package io.jopen.memdb.base.storage;

import io.jopen.memdb.base.annotation.PrimaryKey;

import java.io.Serializable;

/**
 * @author maxuefeng
 * @since 2019/10/22
 */
public class Student implements Serializable {

    @PrimaryKey
    private String id;
    private String name;

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
}
