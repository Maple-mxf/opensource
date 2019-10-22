package io.jopen.core.common.memdb.obsolete;

/**
 * @author maxuefeng
 * @see com.google.common.collect.Table
 * @since 2019/9/24
 */
@Deprecated
public interface MemoryStore {

    /**
     * get current state
     * {@link State}
     *
     * @return object state
     */
    State state();

    /**
     * @return get object name
     */
    String name();
}
