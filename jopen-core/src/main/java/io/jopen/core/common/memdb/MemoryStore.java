package io.jopen.core.common.memdb;

/**
 * @author maxuefeng
 * @see com.google.common.collect.Table
 * @since 2019/9/24
 */
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
