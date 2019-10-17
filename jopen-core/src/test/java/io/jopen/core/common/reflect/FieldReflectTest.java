package io.jopen.core.common.reflect;

import org.junit.Test;

import java.lang.reflect.Field;

/**
 * @author maxuefeng
 */
public class FieldReflectTest {

    /**
     * 判断是否是基本类型
     */
    @Test
    public void testIsPrimitive3() {

        System.err.println(Double.TYPE.isPrimitive());

        System.out.println(ReflectHelper.isPrimitiveOrPrimitiveOfPackage(Double.class));

    }

    static class Student {

        private int age;

        public double weight;
    }

    /**
     * 判断是否是基本类型
     */
    @Test
    public void testIsPrimitive2() {

        Student s = new Student();
        s.weight = 90.0D;

        Field[] fs = s.getClass().getFields();

        for (Field f : fs) {
            System.err.println(f.getType().isPrimitive());
        }
    }

    /**
     * 判断是否是基本类型
     */
    @Test
    public void testIsPrimitive() throws NoSuchFieldException {

        // false
        System.err.println(Integer.class.isPrimitive());

        // true
        System.err.println(int.class.isPrimitive());

        // true TODO TYPE
        System.err.println(Integer.TYPE.isPrimitive());

        //
        Student s = new Student();
        s.weight = 90.0D;

        Field[] fs = s.getClass().getFields();

        for (Field f : fs) {

            System.err.println(f.getType().getSimpleName());

            if (f.getType().isPrimitive()) {
                System.out.println("属于基本类型的变量");
            }

            if (f.getType().getField("TYPE") != null) {
                System.out.println("属于包装类型的变量");
            }
        }
    }

}
