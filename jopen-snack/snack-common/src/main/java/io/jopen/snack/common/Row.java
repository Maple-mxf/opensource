package io.jopen.snack.common;

import com.google.common.base.Preconditions;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * {@link com.google.common.collect.Table.Cell}
 *
 * @author maxuefeng
 * @since 2019/10/24
 */
public final
class Row extends HashMap<String, Object> {

    // 主键
    private Id rowKey;

    public Row(@Nullable Id rowKey) {
        Preconditions.checkNotNull(rowKey);
        this.rowKey = rowKey;
    }

    public final Id getRowKey() {
        return this.rowKey;
    }

    //
    public static Row row(@Nullable Id rowKey, @NonNull Map<String, Object> map) {
        Row row = new Row(rowKey);
        row.putAll(map);
        return row;
    }
}
