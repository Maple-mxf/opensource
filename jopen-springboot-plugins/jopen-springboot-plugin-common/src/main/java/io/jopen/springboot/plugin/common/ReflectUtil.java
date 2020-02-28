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


    public static List<Class<?>> getClasses(String packageName) throws IOException {
        List<Class<?>> classes = Lists.newArrayList();
        boolean recursive = true;
        String packageDirName = packageName.replace('.', '/');
        Enumeration dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
        while (true) {
            label58:
            while (dirs.hasMoreElements()) {
                URL url = (URL) dirs.nextElement();
                String protocol = url.getProtocol();
                if ("file".equals(protocol)) {
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);
                } else if ("jar".equals(protocol)) {
                    JarFile jar = ((JarURLConnection) url.openConnection()).getJarFile();
                    Enumeration entries = jar.entries();

                    while (true) {
                        JarEntry entry;
                        String name;
                        int idx;
                        do {
                            do {
                                if (!entries.hasMoreElements()) {
                                    continue label58;
                                }

                                entry = (JarEntry) entries.nextElement();
                                name = entry.getName();
                                if (name.charAt(0) == '/') {
                                    name = name.substring(1);
                                }
                            } while (!name.startsWith(packageDirName));

                            idx = name.lastIndexOf(47);
                            if (idx != -1) {
                                packageName = name.substring(0, idx).replace('/', '.');
                            }
                        } while (idx == -1 && !recursive);

                        if (name.endsWith(".class") && !entry.isDirectory()) {
                            String className = name.substring(packageName.length() + 1, name.length() - 6);

                            try {
                                classes.add((Class<?>) Class.forName(packageName + '.' + className));
                            } catch (ClassNotFoundException var14) {
                                var14.printStackTrace();
                            }
                        }
                    }
                }
            }
            return classes;
        }
    }

    public static  void findAndAddClassesInPackageByFile(String packageName, String packagePath, boolean recursive, List<Class<?>> classes) {
        File dir = new File(packagePath);
        if (dir.exists() && dir.isDirectory()) {
            File[] dirFiles = dir.listFiles((filex) -> recursive && filex.isDirectory() || filex.getName().endsWith(".class"));
            if (dirFiles != null) {
                int var7 = dirFiles.length;
                for (File file : dirFiles) {
                    if (file.isDirectory()) {
                        findAndAddClassesInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), recursive, classes);
                    } else {
                        String className = file.getName().substring(0, file.getName().length() - 6);

                        try {
                            classes.add((Class<?>) Class.forName(packageName + '.' + className));
                        } catch (ClassNotFoundException var12) {
                            var12.printStackTrace();
                        }
                    }
                }
            }

        }
    }
}
