package io.jopen.snack.common;

import java.io.Serializable;

/**
 * @author maxuefeng
 * @since 2019/10/27
 */
public class DBObject implements Serializable {

    // DB对象的名称
    protected String name;

    // DB对象的创建时间
    protected Long createTs;

    public DBObject(String name, Long createTs) {
        this.name = name;
        this.createTs = createTs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Long createTs) {
        this.createTs = createTs;
    }
}
