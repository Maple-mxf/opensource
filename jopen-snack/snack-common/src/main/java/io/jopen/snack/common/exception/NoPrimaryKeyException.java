package io.jopen.snack.common.exception;

/**
 * <p>{@link SnackRuntimeException}</p>
 *
 * @author maxuefeng
 * @since 2019/10/27
 */
public class NoPrimaryKeyException extends SnackRuntimeException {

    public NoPrimaryKeyException(String msg) {
        super(msg);
    }
}
