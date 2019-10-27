package io.jopen.snack.common;

import java.io.Serializable;

/**
 * <p>{@link DBObject}</p>
 * <p>{@link TableInfo}</p>
 *
 * @author maxuefeng
 * @since 2019/10/27
 */
public final class DatabaseInfo extends DBObject implements Serializable {

    public DatabaseInfo(String name, Long createTs) {
        super(name, createTs);
    }
}
