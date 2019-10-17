package io.jopen.core.common.design.delegate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 事件对象
 *
 * @author maxuefeng
 */
public class Event {

    // 要执行方法的对象
    private Object obj;

    // 要执行的方法名称
    private String methodName;

    // 要执行的方法的参数
    private Object[] params;

    // 要执行方法的参数类型
    private Class[] paramTypes;


    public Event(Object obj, String methodName, Object... params) throws NoSuchMethodException {

        this.obj = obj;
        this.methodName = methodName;
        this.params = params;

        List<Method> ms = Arrays.stream(obj.getClass().getMethods()).filter(
                m -> m.getName().equals(methodName))
                .collect(Collectors.toList());

        if (ms.size() == 0) throw new NoSuchMethodException();

        if (ms.size() == 1) {
            this.paramTypes = ms.get(0).getParameterTypes();

            System.out.println("llll");
        } else {
            Class[] T = new Class[this.params.length];

            for (int i = 0; i < this.params.length; i++) {
                T[i] = params.getClass();
            }

            for (Method m : ms) {
                Class<?>[] ps = m.getParameterTypes();
                if (this.params.length == m.getParameterCount()) {
                    if (Arrays.equals(T, m.getParameterTypes())) {
                        this.paramTypes = T;
                        break;
                    }
                    if (ps[ps.length - 1].isArray()) {
                        this.paramTypes = T;
                        break;
                    }
                } else {

                    if (ps[ps.length - 1].isArray()) {
                        this.paramTypes = T;
                        break;
                    }
                    throw new NoSuchMethodException();
                }
            }
        }
    }


    /**
     * 反射调用
     *
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public void invoke() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        obj.getClass().getMethod(this.methodName, this.paramTypes).invoke(this.obj, this.params);
    }
}






















