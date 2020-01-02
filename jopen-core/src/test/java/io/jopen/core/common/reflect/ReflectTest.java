package io.jopen.core.common.reflect;

import org.junit.Test;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * @see java.beans.PropertyDescriptor
     */
    @Test
    public void testSimpleAPI() throws IntrospectionException {

        // 获取到反射应用对象
        BeanInfo beanInfo = Introspector.getBeanInfo(Student.class);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            System.err.println(propertyDescriptor.getDisplayName());
        }
    }

    @Test
    public void testClassType(){
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
    }
}
