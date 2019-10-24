package io.jopen.memdb.base.storage;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableBiMap;

import java.util.HashMap;

/**
 * {@link com.google.common.collect.Table.Cell}
 *
 * @author maxuefeng
 * @since 2019/10/24
 */
final
class Row<String, Object> extends HashMap<String, Object> {

    // 主键
    private Id rowKey;

    public Row(Id rowKey) {
        Preconditions.checkNotNull(rowKey);
        this.rowKey = rowKey;
    }

    public Id getRowKey() {
        return this.rowKey;
    }

    //
    public static <String, Object> Row<String, Object> row(Id rowKey, ImmutableBiMap<String, Object> biMap) {
        Row<String, Object> row = new Row<>(rowKey);
        row.putAll(biMap);
        return row;
    }
}
