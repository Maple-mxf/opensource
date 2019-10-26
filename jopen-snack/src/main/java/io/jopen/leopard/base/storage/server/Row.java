package io.jopen.leopard.base.storage.server;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableBiMap;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.HashMap;

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
    public static Row row(@Nullable Id rowKey, @NonNull ImmutableBiMap<String, Object> biMap) {
        Row row = new Row(rowKey);
        row.putAll(biMap);
        return row;
    }
}
