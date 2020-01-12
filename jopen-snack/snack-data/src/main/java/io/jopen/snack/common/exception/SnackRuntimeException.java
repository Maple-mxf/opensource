package io.jopen.snack.common.exception;

/**
 * 异常顶级父类  snack异常
 *
 * @author maxuefeng
 * @since 2019/10/27
 */
public class SnackRuntimeException extends RuntimeException {

    protected String msg;

    public SnackRuntimeException(String msg) {
        super(msg);
    }
}
