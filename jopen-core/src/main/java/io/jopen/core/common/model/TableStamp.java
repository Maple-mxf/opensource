package io.jopen.core.common.model;

import com.google.common.collect.Table;

/**
 * 适用于HBase的结果数据装配
 * 每一行数据都会带上时间戳
 *
 * @author maxuefeng
 * @since 2019/8/14
 */
@Deprecated
public class TableStamp<R, C, V> {

    private Table<R, C, V> table;

    private String hBaseRowKey;

    private Long timestamp;

    public TableStamp() {
    }

    public TableStamp(String hBaseRowKey, Table<R, C, V> table, Long timestamp) {
        this.hBaseRowKey = hBaseRowKey;
        this.table = table;
        this.timestamp = timestamp;
    }

    public Table<R, C, V> getTable() {
        return table;
    }

    public void setTable(Table<R, C, V> table) {
        this.table = table;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String gethBaseRowKey() {
        return hBaseRowKey;
    }

    public void sethBaseRowKey(String hBaseRowKey) {
        this.hBaseRowKey = hBaseRowKey;
    }

    @Override
    public String toString() {
        return "TableStamp{" +
                "table=" + table +
                ", hBaseRowKey='" + hBaseRowKey + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
