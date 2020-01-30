package io.jopen.springboot.plugin.common.function;


import com.google.common.base.Strings;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author maxuefeng
 * @since 2019-04-28
 */
public class StringPredicate {

    public static Predicate<String> noNull = s -> !Strings.isNullOrEmpty(s);

    // 代替三元运算符
    public static BiFunction<String, Function<String, String>, String> afterNoNull = (s, fun) -> {
        // 如果不为空
        if (noNull.test(s)) {
            return fun.apply(s);
        }
        return null;
    };
}
