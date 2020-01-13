package io.jopen.orm.hbase.mapper;

import java.lang.reflect.Type;

/**
 * @author maxuefeng
 */
public class EntityPropertyBinding {

    private String name;
    private Type type;
    private String storeFieldName;
    private String nameFullPath;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getStoreFieldName() {
        return storeFieldName;
    }

    public void setStoreFieldName(String storeFieldName) {
        this.storeFieldName = storeFieldName;
    }

    public String getNameFullPath() {
        return nameFullPath;
    }

    public void setNameFullPath(String nameFullPath) {
        this.nameFullPath = nameFullPath;
    }

}
