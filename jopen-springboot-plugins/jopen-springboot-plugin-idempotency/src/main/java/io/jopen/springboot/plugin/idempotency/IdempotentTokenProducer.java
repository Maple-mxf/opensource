package io.jopen.springboot.plugin.idempotency;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * @author maxuefeng
 * @since 2020/1/31
 */
@FunctionalInterface
public interface IdempotentTokenProducer {

    /**
     * @return 返回的是一个Http header中的Key的值  （也就是属于一个Header中的键值对）
     */
    @NonNull
    String applyIdempotentToken();
}
