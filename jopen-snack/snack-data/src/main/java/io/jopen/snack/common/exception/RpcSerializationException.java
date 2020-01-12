package io.jopen.snack.common.exception;

/**
 * {@link SnackRuntimeException}
 *
 * @author maxuefeng
 * @since 2019/10/27
 */
public class RpcSerializationException extends SnackRuntimeException {
    public RpcSerializationException(String msg) {
        super(msg);
    }
}
