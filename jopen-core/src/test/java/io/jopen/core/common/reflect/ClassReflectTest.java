package io.jopen.core.common.reflect;

import org.junit.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author maxuefeng
 */
public class ClassReflectTest {

    /**
     * 判断一个雷是否是合成类  TODO 合成类?
     */
    @Test
    public void testIsSynthetic() {

        // false
        System.err.println(Integer.class.isSynthetic());
    }

    @Test
    public void testClassEquals() {

        Class clazz = List.class;

        // TODO  class.getGenericSuperclass() is null ?
        System.out.println(clazz.getGenericSuperclass());

        // 获取过时标记的字段
        System.out.println("type name: " + clazz.getTypeName());
        System.out.println("componentType: " + clazz.getComponentType());

        for (Type genericInterface : clazz.getGenericInterfaces()) {
            System.out.println(genericInterface);
        }
    }

    /**
     * 判断一个Class是否是Collection
     */
    @Test
    public void testIsCollection() {

        // 获取一个类的泛型
        TypeVariable<Class<List>>[] typeParameters = List.class.getTypeParameters();
        for (TypeVariable<Class<List>> typeParameter : typeParameters) {
            System.out.println(typeParameter.getName());
        }

        // 获取当前类实现的所所有接口
        Type[] genericInterfaces = CopyOnWriteArrayList.class.getGenericInterfaces();
        for (Type genericInterface : genericInterfaces) {
            System.out.println(genericInterface.getTypeName());
        }

        // 获取超级父类 得到的结果是java.lang.Object
        Type genericSuperclass = CopyOnWriteArrayList.class.getGenericSuperclass();
        System.out.println(genericSuperclass.getTypeName());

        // 获取超级父类 得到的结果是java.lang.Object
        Class<? super CopyOnWriteArrayList> superclass = CopyOnWriteArrayList.class.getSuperclass();
        System.out.println(superclass.getName());

        // 判断是否是List的子类  如果不抛出异常则说明属于Collection
        Class<? extends List> aClass = CopyOnWriteArrayList.class.asSubclass(List.class);
    }

    /**
     * 获取一个类的泛型
     */
    @Test
    public void getComponentType() {
        List<Integer> list = new ArrayList<>();

        Class<?> componentType = list.getClass().getComponentType();

        System.out.println(List.class.getComponentType());

        System.err.println(componentType);



        Type type = ((ParameterizedType)list.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        System.err.println(type);
    }
}
