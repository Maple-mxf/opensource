package io.jopen.springboot.plugin.common.function;


import java.util.function.Function;
import java.util.function.ToIntFunction;

/**
 * 数据转换
 *
 * @author maxuefeng
 */
public final class DataTransferFunction {

    // object to int
    public final static ToIntFunction<Object> obj2Int = value -> (int) value;


    // object to toStr
    public final static Function<Object, String> obj2Str = value -> (String) value;


    // object to toStr of wrapper
    public final static Function<Object, String> wrapperStr = String::valueOf;


}
