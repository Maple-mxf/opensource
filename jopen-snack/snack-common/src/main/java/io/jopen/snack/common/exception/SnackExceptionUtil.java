package io.jopen.snack.common.exception;

import com.google.common.base.Predicate;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;


/**
 * @author maxuefeng
 * @see SnackRuntimeException
 * @see RuntimeException
 * @see Exception
 * @see Throwable
 * @see com.google.common.base.Throwables exception utils
 * @since 2019/10/27
 */
public class SnackExceptionUtil {

    public static void checkNull(@Nullable Object object,
                                 @NonNull Class<? extends SnackRuntimeException> exType,
                                 @NonNull String errorMsg) {
        if (object == null) {
            try {
                throw exType.getConstructor(String.class).newInstance(errorMsg);
            } catch (Exception ignored) {
                throw new SnackRuntimeException(String.format("参数为空，异常信息： [ %s ]", errorMsg));
            }
        }
    }

    public static <T> void check(@NonNull Predicate<T> predicate,
                                 @Nullable T arg,
                                 @NonNull Class<? extends SnackRuntimeException> exType,
                                 @NonNull String errorMsg) {
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
