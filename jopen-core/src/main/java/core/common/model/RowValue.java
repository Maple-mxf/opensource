package core.common.model;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * @author maxuefeng
 * @since 2019/8/14
 */
public class RowValue<R, C, V> {

    public static class RowLine<R, C, V> {

        // 未知的列的数量
        public Table<R, C, V> table = HashBasedTable.create();

        public Long version;

        public void put(R family, C column, V value) {
            this.table.put(family, column, value);
        }

        @Override
        public String toString() {
            return "RowLine{" +
                    "table=" + table +
                    ", version=" + version +
                    '}';
        }
    }

    public String rowKey;

    // 一个rowKey对应多个行数据
    public List<RowLine<R, C, V>> rowLines = new ArrayList<>();

    public RowValue(String rowKey) {
        this.rowKey = rowKey;
    }

    public void add(RowLine<R, C, V> rowLine) {
        this.rowLines.add(rowLine);
    }

    @Override
    public String toString() {
        return "RowValue{" +
                "rowKey='" + rowKey + '\'' +
                ", rowLines=" + rowLines +
                '}';
    }
}
