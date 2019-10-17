package io.jopen.core.common.memdb;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author maxuefeng
 * @since 2019/9/24
 */
public class GenericDatabase implements Database {

    private Set<Table> tables = new CopyOnWriteArraySet<>();

    @Override
    public Boolean create(String name) {
        return null;
    }

    @Override
    public Boolean delete(String name) {
        return null;
    }

    @Override
    public List<Table> tables() {
        return null;
    }

    @Override
    public State state() {
        return null;
    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public Boolean contain(String name) {
        return null;
    }

   /* @Override
    public Boolean add(Table<Object, String, Object> table) {
        return null;
    }

    @Override
    public Table<Object, String, Object> get(String name) {
        return null;
    }*/
}
