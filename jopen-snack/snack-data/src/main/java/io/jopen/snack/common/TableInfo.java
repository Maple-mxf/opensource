package io.jopen.snack.common;

import java.util.List;

/**
 * {@link Id}
 * {@link io.jopen.snack.common.annotation.PrimaryKey}
 * {@link DBObject}
 *
 * @author maxuefeng
 * @since 2019/10/27
 */
public final
class TableInfo extends DBObject {

    // table primary key
    private Id id;

    // table columns info
    private List<ColumnInfo> columnInfoList;

    public TableInfo(String name, Long createTs, Id id, List<ColumnInfo> columnInfoList) {
        super(name, createTs);
        this.id = id;
        this.columnInfoList = columnInfoList;
    }

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public List<ColumnInfo> getColumnInfoList() {
        return columnInfoList;
    }

    public void setColumnInfoList(List<ColumnInfo> columnInfoList) {
        this.columnInfoList = columnInfoList;
    }

    @Override
    public String toString() {
        return "TableInfo{" +
                "id=" + id +
                ", columnInfoList=" + columnInfoList +
                ", name='" + name + '\'' +
                ", createTs=" + createTs +
                '}';
    }
}
