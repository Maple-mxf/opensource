package io.jopen.memdb.base.storage;

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

    public ColumnType(Class javaType, String columnName, Boolean primaryKey) {
        this.javaType = javaType;
        this.columnName = columnName;
        this.primaryKey = primaryKey;
    }

    public Class getJavaType() {
        return javaType;
    }

    public void setJavaType(Class javaType) {
        this.javaType = javaType;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public Boolean getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(Boolean primaryKey) {
        this.primaryKey = primaryKey;
    }
}
