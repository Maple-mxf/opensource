package io.jopen.springboot.plugin.param.test;

import io.jopen.springboot.plugin.common.ReflectUtil;
import org.apache.logging.log4j.util.Strings;

import java.lang.annotation.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * 如果当前这个注解加到参数级别上则会校验此方法的部分参数不为空
 * <p>
 * NotNull和CheckParamNotNull配合使用；
 * 前提：如果要检验参数必须在方法级别加CheckParamNotNull
 * <p>
 * 功能定义：如果一个方法只加了CheckParamNotNull注解，默认此方法的所有参数必须不能为空
 * 如果一个方法加了CheckParamNotNull注解，在参数层面也加了NotNull注解，那么只验证加了NotNull注解的参数
 *
 * @author maxuefeng
 * @see CheckParamNotNull
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NotNull {

    // 是否严格校验  如果是严格校验  也需要校验自定义对象的字段是否为空
    boolean strictly() default true;

    // 自定义对象字段数据或者KV形式的数据
    String[] requireFields() default {};

    /**
     * 错误使用NotNull引起的异常
     */
    class NoSuchFieldOfNotNullException extends Exception {

        NoSuchFieldOfNotNullException(String msg) {
            super(msg);
        }
    }

    /**
     * 空参数异常
     */
    class NullParamException extends RuntimeException {

        public NullParamException(String msg) {
            super(msg);
        }

    }

    /**
     *
     */
    class Util {
        /**
         * @param target
         * @param strictly 是否严格校验
         * @return
         */
        public static boolean isEmpty(Object target, boolean strictly, String[] fields) throws NoSuchFieldOfNotNullException {

            if (target == null) {
                return true;
            }

            if (target instanceof String) {

                if (Strings.isBlank(target.toString()) || "null".equals(target.toString())) return true;
            }

            if (strictly) {

                // 集合类型
                if (target instanceof Collection) {
                    Collection c = (Collection) target;
                    return c.size() == 0;
                }

                // Map类型
                if (target instanceof Map) {

                    Map m = (Map) target;
                    if (fields != null && fields.length > 0) {
                        // 只要存在一个空数据则返回true
                        return Arrays.stream(fields).anyMatch(t -> m.get(t) == null);
                    } else {
                        return m.size() == 0;
                    }
                }

                // 数组对象
                if (target instanceof Object[]) {
                    Object[] o = (Object[]) target;
                    return o.length == 0;
                }

                // 自定义对象[自定义对象需要判断指定的字段不可为空]


                if (fields != null && fields.length > 0) {

                    for (String field : fields) {
                        try {
                            Object value = ReflectUtil.getObjFiledValue(target, field);

                            if (value == null) {
                                return true;
                            }
                        } catch (NoSuchFieldException e) {
                            throw new NoSuchFieldOfNotNullException(String.format("注解NotNull指定的属性[ %s ]错误, 对象 [ %s ] 无属性 [ %s ]", field, target.getClass().getName(), field));
                        }

                    }
                }

                // 判定所有字段都不可为空
                else {
                    // 判断指定字段不可为空
                    Map<String, Object> filedValues = ReflectUtil.getObjFiledValues(target);

                    // 校验所有字段
                    return filedValues.values().stream().anyMatch(Objects::isNull);
                }


            }
            return false;
        }
    }
}
