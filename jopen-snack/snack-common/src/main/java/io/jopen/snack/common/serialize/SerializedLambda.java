package io.jopen.snack.common.serialize;

import org.apache.commons.lang3.SerializationUtils;

import java.io.*;

/**
 * @author maxuefeng
 * @see Object
 * @since 2019/10/25
 */
public class SerializedLambda implements Serializable {

    private static final long serialVersionUID = 8025925345765570181L;

    private Class<?> capturingClass;
    private String functionalInterfaceClass;
    private String functionalInterfaceMethodName;
    private String functionalInterfaceMethodSignature;
    private String implClass;
    private String implMethodName;
    private String implMethodSignature;
    private int implMethodKind;
    private String instantiatedMethodType;
    private Object[] capturedArgs;

    /**
     * 通过反序列化转换 lambda 表达式，该方法只能序列化 lambda 表达式，不能序列化接口实现或者正常非 lambda 写法的对象
     *
     * @param lambda lambda对象
     * @return 返回解析后的 SerializedLambda
     */
    public static SerializedLambda resolve(SFunction lambda) {
        if (!lambda.getClass().isSynthetic()) {
            throw new RuntimeException("该方法仅能传入 lambda 表达式产生的合成类");
        }
        try (ObjectInputStream objIn = new ObjectInputStream(new ByteArrayInputStream(SerializationUtils.serialize(lambda))) {
            @Override
            protected Class<?> resolveClass(ObjectStreamClass objectStreamClass) throws IOException, ClassNotFoundException {
                Class<?> clazz = super.resolveClass(objectStreamClass);
                return clazz == java.lang.invoke.SerializedLambda.class ? SerializedLambda.class : clazz;
            }
        }) {
            Object object = objIn.readObject();
            System.err.println(object.getClass());
            return (SerializedLambda) object;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException(String.format("This is impossible to happen %s ", e.getCause()));
        }
    }

    /**
     * 获取接口 class
     *
     * @return 返回 class 名称
     */
    public String getFunctionalInterfaceClassName() {
        return normalName(functionalInterfaceClass);
    }

    /**
     * 获取实现的 class
     *
     * @return 实现类
     */
    public Class getImplClass() {
        return ClassHelper.toClassConfident(getImplClassName());
    }

    /**
     * 获取 class 的名称
     *
     * @return 类名
     */
    public String getImplClassName() {
        return normalName(implClass);
    }

    /**
     * 获取实现者的方法名称
     *
     * @return 方法名称
     */
    public String getImplMethodName() {
        return implMethodName;
    }

    /**
     * 正常化类名称，将类名称中的 / 替换为 .
     *
     * @param name 名称
     * @return 正常的类名
     */
    private String normalName(String name) {
        return name.replace('/', '.');
    }

    /**
     * @return 字符串形式
     */
    @Override
    public String toString() {
        return String.format("%s -> %s::%s", getFunctionalInterfaceClassName(), getImplClass().getSimpleName(),
                implMethodName);
    }
}
