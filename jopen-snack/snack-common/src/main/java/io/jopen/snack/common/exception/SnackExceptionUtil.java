package io.jopen.snack.common.exception;


import java.util.function.Predicate;

/**
 * @author maxuefeng
 * @see SnackRuntimeException
 * @since 2019/10/27
 */
public class SnackExceptionUtil {

    public static void checkNull(Object object, Class<? extends SnackRuntimeException> exType, String errorMsg) {
        if (object == null) {
            try {
                throw exType.getConstructor(String.class).newInstance(errorMsg);
            } catch (Exception ignored) {
                throw new SnackRuntimeException(String.format("参数为空，异常信息： [ %s ]", errorMsg));
            }
        }
    }

    public static void check(Predicate<Object> predicate, Object arg, Class<? extends SnackRuntimeException> exType, String errorMsg) {
        boolean test = predicate.test(arg);
        if (!test) {
            try {
                throw exType.getConstructor(String.class).newInstance(errorMsg);
            } catch (Exception ignored) {
                throw new SnackRuntimeException(String.format("异常信息： [ %s ]", errorMsg));
            }
        }
    }
}
