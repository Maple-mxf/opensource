package io.jopen.springboot.plugin.limit;

/**
 * @author maxuefeng
 * @since 2020/2/15
 */
public class LimitException extends RuntimeException {

    public LimitException(String errMsg) {
        super(errMsg);
    }
}
