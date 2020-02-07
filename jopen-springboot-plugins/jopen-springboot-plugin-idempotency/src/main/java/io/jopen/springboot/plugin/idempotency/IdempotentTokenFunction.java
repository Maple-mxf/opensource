package io.jopen.springboot.plugin.idempotency;

/**
 * @author maxuefeng
 * @since 2020/2/7
 */
public interface IdempotentTokenFunction {

    /**
     * @return
     * @see EnableJopenIdempotent#idempotentTokenKey()
     */
    String setupTokenKey();

    /**
     * @return
     * @see EnableJopenIdempotent#idempotentTokenLocation()
     */
    TokenLocation setupTokenLocation();
}
