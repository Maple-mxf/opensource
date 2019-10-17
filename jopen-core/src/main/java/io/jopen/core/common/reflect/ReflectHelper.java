package io.jopen.core.common.reflect;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 反射工具类
 *
 * @author maxuefeng
 */
public class ReflectHelper {

    @Deprecated
    private final static Set<String> primitive = new HashSet<>();

    private final Class<?> type;

    private final Object object;

    private ReflectHelper(final Class<?> type, Object object) {
        this.type = type;
        this.object = object;
    }


    private Class<?>[] getArgsType(final Object... args) {
        if (args == null) return new Class[0];
        Class<?>[] result = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            Object value = args[i];

            // TODO Null.class
            result[i] = value == null ? Void.class : value.getClass();
        }
        return result;
    }


    /**
     * 判断一个变量是否属于基本类型或者属于基本类型的变量
     *
     * @param T
     * @return
     */
    public static boolean isPrimitiveOrPrimitiveOfPackage(Class<?> T) {

        if (T.isPrimitive())
            return true;

        try {
            Field f = T.getField("TYPE");

            if (f == null) return false;

        } catch (NoSuchFieldException e) {
            return false;
        }

        return false;
    }

    public Object get() {
        return this.object;
    }

    private Object unwrap(Object object) {
        if (object instanceof ReflectHelper) {
            return ((ReflectHelper) object).get();
        }
        return object;
    }

    /*private Field getAccessibleField(String name) {
        Class<?> type = type();
        try {
            return accessible(type.getField(name));
        } catch (NoSuchFieldException e) {
            do {
                try {
                    return accessible(type.getDeclaredField(name));
                } catch (NoSuchFieldException ignore) {
                }
                type = type.getSuperclass();
            } while (type != null);
            throw new RuntimeException(e);
        }
    }*/

    /**
     * 获取字段名称
     *
     * @param name
     * @return
     * @throws IllegalAccessException
     */
   /* private Field getField(String name) throws IllegalAccessException {

        Field field = getAccessibleField(name);

        if ((field.getModifiers() & Modifier.FINAL) == Modifier.FINAL) {

            try {

                Field modifiersField = Field.class.getDeclaredField("modifiers");
                modifiersField.setAccessible(true);
                modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

            } catch (NoSuchFieldException ignore) {
                // runs in android will happen
            }
        }
        return field;
    }*/

    /**
     * 设置反射的字段
     *
     * @param name  字段名
     * @param value 字段值
     * @return {@link ReflectHelper}
     */
/*    public ReflectHelper field(String name, Object value) {
        try {
            Field field = getField(name);
//            field.set(object, unwrap(value));

            // TODO ??
            field.set("", unwrap(value));
            return this;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    *//**
     * 设置反射的字段
     *
     * @param name 字段名
     * @return {@link ReflectHelper}
     *//*
    public ReflectHelper field(final String name) {
        try {
            Field field = getField(name);
            return new ReflectHelper(field.getType(), field.get(object));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }*/


    /**
     * 从包package中获取所有的Class
     *
     * @param packageName
     * @return
     */
    public static List<Class<?>> getClasses(String packageName) {

        //第一个class类的集合
        List<Class<?>> classes = Lists.newArrayList();

        //是否循环迭代
        boolean recursive = true;

        //获取包的名字 并进行替换
        String packageDirName = packageName.replace('.', '/');

        //定义一个枚举的集合 并进行循环来处理这个目录下的things
        Enumeration<URL> dirs;

        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);

            //循环迭代下去
            while (dirs.hasMoreElements()) {

                //获取下一个元素
                URL url = dirs.nextElement();

                //得到协议的名称
                String protocol = url.getProtocol();

                //如果是以文件的形式保存在服务器上
                if ("file".equals(protocol)) {

                    //获取包的物理路径
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");

                    //以文件的方式扫描整个包下的文件 并添加到集合中
                    findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);

                } else if ("jar".equals(protocol)) {

                    //如果是jar包文件
                    //定义一个JarFile
                    JarFile jar;
                    try {

                        //获取jar
                        jar = ((JarURLConnection) url.openConnection()).getJarFile();

                        //从此jar包 得到一个枚举类
                        Enumeration<JarEntry> entries = jar.entries();

                        //同样的进行循环迭代
                        while (entries.hasMoreElements()) {

                            //获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
                            JarEntry entry = entries.nextElement();

                            String name = entry.getName();

                            //如果是以/开头的
                            if (name.charAt(0) == '/') {

                                //获取后面的字符串
                                name = name.substring(1);
                            }

                            //如果前半部分和定义的包名相同
                            if (name.startsWith(packageDirName)) {
                                int idx = name.lastIndexOf('/');

                                //如果以"/"结尾 是一个包
                                if (idx != -1) {

                                    //获取包名 把"/"替换成"."
                                    packageName = name.substring(0, idx).replace('/', '.');
                                }

                                //如果可以迭代下去 并且是一个包
                                if ((idx != -1) || recursive) {

                                    //如果是一个.class文件 而且不是目录
                                    if (name.endsWith(".class") && !entry.isDirectory()) {

                                        //去掉后面的".class" 获取真正的类名
                                        String className = name.substring(packageName.length() + 1, name.length() - 6);
                                        try {

                                            //添加到classes
                                            classes.add(Class.forName(packageName + '.' + className));
                                        } catch (ClassNotFoundException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return classes;
    }


    /**
     * 以文件的形式来获取包下的所有Class
     *
     * @param packageName
     * @param packagePath
     * @param recursive
     * @param classes
     */
    private static void findAndAddClassesInPackageByFile(String packageName, String packagePath, final boolean recursive, List<Class<?>> classes) {

        //获取此包的目录 建立一个File
        File dir = new File(packagePath);

        //如果不存在或者 也不是目录就直接返回
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }

        //如果存在 就获取包下的所有文件 包括目录
        //自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
        File[] dirfiles = dir.listFiles(file -> (recursive && file.isDirectory()) || (file.getName().endsWith(".class")));

        //循环所有文件
        if (dirfiles != null) {
            for (File file : dirfiles) {

                //如果是目录 则继续扫描

                if (file.isDirectory()) {
                    findAndAddClassesInPackageByFile(packageName + "." + file.getName(),
                            file.getAbsolutePath(),
                            recursive,
                            classes);
                } else {

                    //如果是java类文件 去掉后面的.class 只留下类名
                    String className = file.getName().substring(0, file.getName().length() - 6);

                    try {
                        //添加到集合中去
                        classes.add(Class.forName(packageName + '.' + className));

                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    /**
     * 获取一个对象的属性和属性的值
     *
     * @param obj
     * @return
     */
    public static Map<String, Object> getObjFiledValues(Object obj) {

        if (obj == null) return Maps.newHashMap();

        Field[] fields = obj.getClass().getDeclaredFields();

        Map<String, Object> fieldValues = new HashMap<>();

        for (Field field : fields) {

            field.setAccessible(true);

            // 字段名
            String filedName = field.getName();

            // 字段值
            Object value = null;
            try {
                value = field.get(obj);

            } catch (IllegalAccessException ignored) {
            }

            fieldValues.put(filedName, value);
        }

        return fieldValues;
    }

    /**
     * @param obj
     * @param fieldName
     * @return
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     */
    public static Object getObjFiledValue(Object obj, String fieldName) throws NoSuchFieldException {

        if (obj == null || StringUtils.isBlank(fieldName)) return null;

        Field field = obj.getClass().getDeclaredField(fieldName);

        Object filedValue = null;

        // 设为可访问
        field.setAccessible(true);
        try {
            // 字段值
            filedValue = field.get(obj);
        } catch (IllegalAccessException ignored) {
        }

        return filedValue;
    }
}
