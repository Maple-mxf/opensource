package io.jopen.core.common.reflect;

import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author maxuefeng
 * @since 2019/10/24
 */
public class MyQueue<AnyType> {

    /**
     * 队列数组容量
     */
    private int capacity;
    /**
     * 封装的数组
     */
    private AnyType[] data;

    //其他属性略过

    // 创建
    public MyQueue(Class<AnyType> type, int capacity) {
        this.capacity = capacity;
        //法一：通过强转数组
        //data = (AnyType[]) new Object[capacity];//这样做可以实现需求，但底层毕竟是Object[]类型，
        //我想要真正的传入的泛型类型的数组

        //法二：通过传入AnyType的clazz对象
//		data = getArray(type,capacity);

        //法三：通过获取传来的泛型类来构建数组，可以少传一个参数。（待解决！！！！）
        Class<AnyType> clazz = getGenType();//此处返回的只是AnyType类的class，
        //并不是我所要的泛型类的class，不知该如何获得
        data = getArray(clazz, capacity);//由于构建的只是AnyType的数组，因此，添加Integer元素会抛异常Exception in
        // thread "main" java.lang.ArrayStoreException: java.lang.Integer
    }

    @SuppressWarnings("unchecked")
    private <E> E[] getArray(Class<E> e, int capacity) {
        return (E[]) Array.newInstance(e, capacity);

    }

    //获取传入的泛型类
    private Class<AnyType> getGenType() {
        Type[] types = getClass().getTypeParameters();
        Class<AnyType> clazz = (Class<AnyType>) types[0].getClass();
        // System.out.println(Arrays.toString(types));
        return clazz;
    }

    public static void main(String[] args) {
        MyQueue<Integer> qu = new MyQueue<>(Integer.class,4);
        System.err.println(getActualTypeArgument(qu.getClass()));
    }

    public static Class<?> getActualTypeArgument(Class<?> clazz) {
        Class<?> entitiClass = null;
        Type genericSuperclass = clazz.getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass)
                    .getActualTypeArguments();
            if (actualTypeArguments != null && actualTypeArguments.length > 0) {
                entitiClass = (Class<?>) actualTypeArguments[0];
            }
        }

        return entitiClass;
    }
}
