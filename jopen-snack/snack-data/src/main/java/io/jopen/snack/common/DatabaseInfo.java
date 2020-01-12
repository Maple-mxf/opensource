package io.jopen.snack.common;

import java.io.Serializable;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * <p>{@link DBObject}</p>
 * <p>{@link TableInfo}</p>
 *
 * @author maxuefeng
 * @since 2019/10/27
 */
public final class DatabaseInfo extends DBObject implements Serializable {

    private CopyOnWriteArraySet<TableInfo> tableInfoSet = new CopyOnWriteArraySet<>();

    public DatabaseInfo(String name, Long createTs) {
        super(name, createTs);
    }

    public CopyOnWriteArraySet<TableInfo> getTableInfoSet() {
        return tableInfoSet;
    }

    public void setTableInfoSet(CopyOnWriteArraySet<TableInfo> tableInfoSet) {
        this.tableInfoSet = tableInfoSet;
    }
}
