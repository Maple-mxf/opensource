package io.jopen.core.common.json;

import com.alibaba.fastjson.JSONPath;
import lombok.Getter;
import lombok.Setter;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * @author maxuefeng
 * @see com.alibaba.fastjson.JSONPath
 * @since 2019-11-13
 */
public class JsonElesParser {

    @Getter
    @Setter
    public static class ParamTypeException extends RuntimeException {
        private String msg;

        public ParamTypeException(String msg) {
            super(msg);
        }
    }

    private Object target;

    private JsonElesParser(Object target) {
        this.target = target;
    }

    public static JsonElesParser build(@NonNull Object target) {
        return new JsonElesParser(target);
    }

    public String toStr(String path) {
        try {
            return (String) JSONPath.eval(target, path);
        } catch (ClassCastException ignored) {
            throw new ParamTypeException("参数类型错误");
        }
    }


    public Long toLong(String path) {
        try {
            return (Long) JSONPath.eval(target, path);

        } catch (ClassCastException ignored) {
            throw new ParamTypeException("参数类型错误");
        }
    }

    public Double toDouble(String path) {
        try {
            return (Double) JSONPath.eval(target, path);

        } catch (ClassCastException ignored) {
            throw new ParamTypeException("参数类型错误");
        }
    }
}
