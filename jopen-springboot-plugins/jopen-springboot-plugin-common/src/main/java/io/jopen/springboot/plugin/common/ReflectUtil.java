package io.jopen.springboot.plugin.common;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 反射工具类
 *
 * @author maxuefeng
 */
public class ReflectUtil {

    /**
     * @param obj
     */
    public static Map<String, Object> getObjFiledValues(Object obj) {

        if (obj == null) return new HashMap<>();

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

        if (obj == null || Strings.isNullOrEmpty(fieldName)) return null;

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


    /**
     * 从包package中获取所有的Class
     *
     * @param packageName
     * @return
     */
    public static List<Class<?>> getClasses(String packageName) throws IOException {
        List<Class<?>> classes = Lists.newArrayList();

        boolean recursive = true;
        String packageDirName = packageName.replace('.', '/');
        Enumeration<URL> dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);

        while (dirs.hasMoreElements()) {
            URL url = dirs.nextElement();
            String protocol = url.getProtocol();
            if ("file".equals(protocol)) {
                String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);
            } else if ("jar".equals(protocol)) {
                JarFile jar = ((JarURLConnection) url.openConnection()).getJarFile();
                Enumeration<JarEntry> entries = jar.entries();

                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    String name = entry.getName();
                    if (name.charAt(0) == '/') {
                        name = name.substring(1);
                    }
                    if (name.startsWith(packageDirName)) {
                        int idx = name.lastIndexOf('/');
                        if (idx != -1) {
                            //获取包名 把"/"替换成"."
                            packageName = name.substring(0, idx).replace('/', '.');
                        }
                        if ((idx != -1) || recursive) {
                            if (name.endsWith(".class") && !entry.isDirectory()) {
                                String className = name.substring(packageName.length() + 1, name.length() - 6);
                                try {
                                    classes.add(Class.forName(packageName + '.' + className));
                                } catch (ClassNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
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
        File[] dirFiles = dir.listFiles(file -> (recursive && file.isDirectory()) || (file.getName().endsWith(".class")));

        //循环所有文件
        if (dirFiles != null) {
            for (File file : dirFiles) {

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

}
