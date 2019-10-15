package core.common.memdb;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import io.jopen.core.common.memdb.annotation.Util;

import java.lang.reflect.Field;


/**
 * @author maxuefeng
 * @since 2019/9/24
 */
public class MemoryDBTemplate {

    private Database database;

    public MemoryDBTemplate(String name) {
        assert name != null;
        Boolean contain = StorageDisk.instance.contain(name);
        if (!contain) {
            throw new IllegalArgumentException("database  not  exsit");
        }
        this.database = StorageDisk.instance.getDatabase(name);
    }

    public <T> void save(T t) throws IllegalAccessException {
        
        Table<Object, String, Object> table = null;
        String tableName = Util.entityVal(t.getClass());
        if (!database.contain(tableName)) {
            // create table
            table = createTable(t);
        } else {
            table = database.get(tableName);
        }
        assert table != null;

        // put/save entity data
        Object idVal = Util.idVal(t);
        for (Field field : t.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = field.get(t);
            table.put(idVal, fieldName, value);
        }
        // save success
    }

    public <T> void delete(T t) {
    }

    public <T> void modify(T t) {
    }

    /**
     * create table by entity object
     * {@link com.google.common.collect.Table}
     *
     * @param t   entity object{@link io.jopen.core.common.memdb.annotation.Entity}{@link io.jopen.core.common.memdb.annotation.Field}
     * @param <T> all type entity
     */
    public <T> Table<Object, String, Object> createTable(T t) {

        // get id value
        // Object idVal = Util.idVal(t);
        Table<Object, String, Object> table = HashBasedTable.create();

        // add
        this.database.add(table);

        return table;
    }

}
