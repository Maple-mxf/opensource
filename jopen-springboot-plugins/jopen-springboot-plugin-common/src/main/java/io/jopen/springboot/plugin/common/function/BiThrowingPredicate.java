package io.jopen.springboot.plugin.common.function;

import java.util.Map;

/**
 * @author maxuefeng
 * @see java.util.function.Predicate
 */
@FunctionalInterface
public interface BiThrowingPredicate<T, U> {
    /**
     * 如果失败（false）了  则需要定位失败信息（Object）
     * 传统的Predicate已经不满足需求了
     *
     * @param t
     * @return
     */
    Map.Entry<Boolean, Object> test(T t, U u) throws Throwable;

}
