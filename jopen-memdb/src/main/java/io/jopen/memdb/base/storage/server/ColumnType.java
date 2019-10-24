package io.jopen.memdb.base.storage.server;

import com.google.common.base.Preconditions;

/**
 * @author maxuefeng
 * @since 2019/10/24
 */
final
class ColumnType {

    // 对应的java数据类型
    private Class javaType;

    // 列名称
    private String columnName;

    // 是否为主键
    private Boolean primaryKey;

    ColumnType(Class javaType, String columnName) {
        this(javaType, columnName, false);
    }

    ColumnType(Class javaType, String columnName, Boolean primaryKey) {
        Preconditions.checkNotNull(javaType);
        Preconditions.checkNotNull(columnName);
        Preconditions.checkNotNull(primaryKey);

        this.javaType = javaType;
        this.columnName = columnName;
        this.primaryKey = primaryKey;
    }

    public Class getJavaType() {
        return javaType;
    }

    public String getColumnName() {
        return columnName;
    }


    public Boolean getPrimaryKey() {
        return primaryKey;
    }
}
