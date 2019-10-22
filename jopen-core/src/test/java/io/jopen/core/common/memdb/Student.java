package io.jopen.core.common.memdb;

import java.io.Serializable;

/**
 * @author maxuefeng
 * @since 2019/10/22
 */
public class Student implements Serializable {

    public String id;
    public String name;

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
