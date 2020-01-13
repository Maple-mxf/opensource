package io.jopen.orm.hbase.query.criterion;

import org.apache.commons.lang3.SerializationUtils;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.*;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * An base on lambda method reference parse property name
 * this builder has a local cache; this will be occupy a certain size
 * memory;Lambda is not an really reference,It is an Language sugar
 * <p>
 * {@link SFunction}
 * {@link Orderings}
 * {@link Projections}
 *
 * @author maxuefeng
 * @since 2020-01-13
 */
public interface LambdaBuilder {

    /**
     * an storage lambda serializedLambda Object hashMap local cache
     */
    ConcurrentHashMap<Class<?>, WeakReference<SerializedLambda>> SF_CACHE = new ConcurrentHashMap<>(200);


    /**
     * <p>
     * 解析 lambda 表达式
     * </p>
     *
     * @param func 需要解析的 lambda 对象
     * @param <T>  类型，被调用的 Function 对象的目标类型
     * @return 返回解析后的结果
     */
    static <T> SerializedLambda resolve(SFunction<T, ?> func) {
        Class clazz = func.getClass();
        return Optional.ofNullable(SF_CACHE.get(clazz))
                .map(WeakReference::get)
                .orElseGet(() -> {
                    SerializedLambda lambda = SerializedLambda.resolve(func);
                    SF_CACHE.put(clazz, new WeakReference<>(lambda));
                    return lambda;
                });
    }


    final class SerializedLambda implements Serializable {

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
                return (SerializedLambda) objIn.readObject();
            } catch (ClassNotFoundException | IOException e) {
                throw new RuntimeException("");
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

    final class ClassHelper {

        /**
         * 代理 class 的名称
         */
        private static final List<String> PROXY_CLASS_NAMES = Arrays.asList(
                "net.sf.cglib.proxy.Factory"
                // cglib
                , "org.springframework.cglib.proxy.Factory"
                , "javassist.io.jopen.springboot.encryption.util.proxy.ProxyObject"
                // javassist
                , "org.apache.ibatis.javassist.io.jopen.springboot.encryption.util.proxy.ProxyObject");

        private ClassHelper() {
        }

        /**
         * 判断是否为代理对象
         *
         * @param clazz 传入 class 对象
         * @return 如果对象class是代理 class，返回 true
         */
        public static boolean isProxy(Class<?> clazz) {
            if (clazz != null) {
                for (Class<?> cls : clazz.getInterfaces()) {
                    if (PROXY_CLASS_NAMES.contains(cls.getName())) {
                        return true;
                    }
                }
            }
            return false;
        }

        /**
         * 获取当前对象的 class
         *
         * @param clazz 传入
         * @return 如果是代理的class，返回父 class，否则返回自身
         */
        public static Class<?> getUserClass(Class<?> clazz) {
            return isProxy(clazz) ? clazz.getSuperclass() : clazz;
        }

        /**
         * 获取当前对象的class
         *
         * @param object 对象
         * @return 返回对象的 user class
         */
        public static Class<?> getUserClass(@NonNull Object object) {
            return getUserClass(object.getClass());
        }

        /**
         * 根据指定的 class ， 实例化一个对象，根据构造参数来实例化
         * <p>
         * 在 java9 及其之后的版本 Class.newInstance() 方法已被废弃
         *
         * @param clazz 需要实例化的对象
         * @param <T>   类型，由输入类型决定
         * @return 返回新的实例
         */
        public static <T> T newInstance(Class<T> clazz) {
            try {
                Constructor<T> constructor = clazz.getDeclaredConstructor();
                constructor.setAccessible(true);
                return constructor.newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(String.format("实例化对象时出现错误,请尝试给 %s 添加无参的构造方法", clazz.getName()));
            }
        }

        /**
         * 请仅在确定类存在的情况下调用该方法
         *
         * @param name 类名称
         * @return 返回转换后的 Class
         */
        public static Class<?> toClassConfident(String name) {
            try {
                return Class.forName(name);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(String.format("找不到指定的class！请仅在明确确定会有 class 的时候，调用该方法 %s", e.getCause()));
            }
        }
    }

}
