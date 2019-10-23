package io.jopen.core.common.reflect;

import org.junit.Test;

/**
 * @author maxuefeng
 */
public class ReflectTest {

    class Student {

        private String name;

        private String email;

        public void doSomethings() {
            System.err.println("Hello World");
        }
    }

    @Test
    public void testSimpleAPI() {

        // 获取到反射应用对象
        Reflect reflect = Reflect.onClass(Student.class.getName());

        // 调用Student的方法
        reflect.call("doSomethings");

    }
}
