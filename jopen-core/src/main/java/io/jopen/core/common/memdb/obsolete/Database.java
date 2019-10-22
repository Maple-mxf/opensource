package io.jopen.core.common.memdb.obsolete;

import java.util.List;

/**
 * @author maxuefeng
 * @since 2019/9/24
 */
@Deprecated
public interface Database extends MemoryStore {

    /**
     * create table in memory
     * {@link Boolean}
     *
     * @param name database name
     * @return create success or fail
     */
    Boolean create(String name);

    /**
     * delete table in memory
     * {@link Boolean}
     *
     * @param name database name
     * @return delete object
     */
    Boolean delete(String name);

    /**
     * {@link Table}
     *
     * @return get all tables object
     */
    List<Table> tables();

    /**
     * {@link Table}
     *
     * @return return this database contain the table by name
     */
    Boolean contain(String name);


    /**
     * {@link Table}
     *
     * @param table created table object
     * @return success or fail
     */
    // Boolean add(Table<Object, String, Object> table);

    /**
     * get object in memory
     * {@link Boolean}
     *
     * @param name database name
     * @return delete object
     */
    // Table<Object, String, Object> get(String name);
}
