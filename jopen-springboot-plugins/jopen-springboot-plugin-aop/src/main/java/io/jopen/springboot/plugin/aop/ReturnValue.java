package io.jopen.springboot.plugin.aop;

import java.io.Serializable;
import java.util.HashMap;
import java.util.function.Consumer;

/**
 * @author maxuefeng
 */
public class ReturnValue extends HashMap<String, Object> implements Serializable, Cloneable {

    private ReturnValue() {
    }

    /**
     * 无参数
     *
     * @return
     */
    public static ReturnValue empty() {
        return new ReturnValue();
    }

    /**
     *
     * @param consumer
     * @param <T>
     */
    public <T> void ifPresent(Consumer<ReturnValue> consumer) {

        if (isNotEmpty() && consumer != null) {
            consumer.accept(this);
        }
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }


    /**
     * 避免使用重载方法调用  方法重载互相调用是Java编程的标准，
     * 但是此处不提倡，因为会插入Null，Key或者Value为Null
     *
     * @param k1
     * @param v1
     * @return
     */
    public static ReturnValue of(
            String k1, Object v1) {

        ReturnValue returnValue = new ReturnValue();
        returnValue.put(k1, v1);

        return returnValue;
    }

    public static ReturnValue of(
            String k1, Object v1,
            String k2, Object v2) {

        ReturnValue returnValue = new ReturnValue();

        returnValue.put(k1, v1);
        returnValue.put(k2, v2);

        return returnValue;
    }

    public static ReturnValue of(
            String k1, Object v1,
            String k2, Object v2,
            String k3, Object v3) {

        ReturnValue returnValue = new ReturnValue();

        returnValue.put(k1, v1);
        returnValue.put(k2, v2);
        returnValue.put(k3, v3);

        return returnValue;
    }

    public static ReturnValue of(
            String k1, Object v1,
            String k2, Object v2,
            String k3, Object v3,
            String k4, Object v4) {

        ReturnValue returnValue = new ReturnValue();

        returnValue.put(k1, v1);
        returnValue.put(k2, v2);
        returnValue.put(k3, v3);
        returnValue.put(k4, v4);

        return returnValue;
    }

    /**
     * 避免外部
     *
     * @param key
     * @param <T>
     * @return
     */
    public <T> T value(String key) {
        Object o = this.get(key);
        return (T) o;
    }


}
