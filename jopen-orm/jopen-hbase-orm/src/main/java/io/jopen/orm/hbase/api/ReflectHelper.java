package io.jopen.orm.hbase.api;

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
 * @author maxuefeng
 * @since 2020/1/12
 */
public class ReflectHelper {
    private final Class<?> type;
    private final Object object;

    private ReflectHelper(Class<?> type, Object object) {
        this.type = type;
        this.object = object;
    }

    private Class<?>[] getArgsType(Object... args) {
        if (args == null) {
            return new Class[0];
        } else {
            Class<?>[] result = new Class[args.length];

            for(int i = 0; i < args.length; ++i) {
                Object value = args[i];
                result[i] = value == null ? Void.class : value.getClass();
            }

            return result;
        }
    }

    public static boolean isPrimitiveOrPrimitiveOfPackage(Class<?> T) {
        if (T.isPrimitive()) {
            return true;
        } else {
            try {
                Field f = T.getField("TYPE");
                return f == null ? false : false;
            } catch (NoSuchFieldException var2) {
                return false;
            }
        }
    }

    public Object get() {
        return this.object;
    }

    private Object unwrap(Object object) {
        return object instanceof ReflectHelper ? ((ReflectHelper)object).get() : object;
    }

    public static List<Class<?>> getClasses(String packageName) {
        List<Class<?>> classes = Lists.newArrayList();
        boolean recursive = true;
        String packageDirName = packageName.replace('.', '/');

        try {
            Enumeration dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);

            while(true) {
                label67:
                while(dirs.hasMoreElements()) {
                    URL url = (URL)dirs.nextElement();
                    String protocol = url.getProtocol();
                    if ("file".equals(protocol)) {
                        String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                        findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);
                    } else if ("jar".equals(protocol)) {
                        try {
                            JarFile jar = ((JarURLConnection)url.openConnection()).getJarFile();
                            Enumeration entries = jar.entries();

                            while(true) {
                                JarEntry entry;
                                String name;
                                int idx;
                                do {
                                    do {
                                        if (!entries.hasMoreElements()) {
                                            continue label67;
                                        }

                                        entry = (JarEntry)entries.nextElement();
                                        name = entry.getName();
                                        if (name.charAt(0) == '/') {
                                            name = name.substring(1);
                                        }
                                    } while(!name.startsWith(packageDirName));

                                    idx = name.lastIndexOf(47);
                                    if (idx != -1) {
                                        packageName = name.substring(0, idx).replace('/', '.');
                                    }
                                } while(idx == -1 && !recursive);

                                if (name.endsWith(".class") && !entry.isDirectory()) {
                                    String className = name.substring(packageName.length() + 1, name.length() - 6);

                                    try {
                                        classes.add(Class.forName(packageName + '.' + className));
                                    } catch (ClassNotFoundException var14) {
                                        var14.printStackTrace();
                                    }
                                }
                            }
                        } catch (IOException var15) {
                            var15.printStackTrace();
                        }
                    }
                }

                return classes;
            }
        } catch (IOException var16) {
            var16.printStackTrace();
            return classes;
        }
    }

    private static void findAndAddClassesInPackageByFile(String packageName, String packagePath, boolean recursive, List<Class<?>> classes) {
        File dir = new File(packagePath);
        if (dir.exists() && dir.isDirectory()) {
            File[] dirfiles = dir.listFiles((filex) -> {
                return recursive && filex.isDirectory() || filex.getName().endsWith(".class");
            });
            if (dirfiles != null) {
                File[] var6 = dirfiles;
                int var7 = dirfiles.length;

                for(int var8 = 0; var8 < var7; ++var8) {
                    File file = var6[var8];
                    if (file.isDirectory()) {
                        findAndAddClassesInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), recursive, classes);
                    } else {
                        String className = file.getName().substring(0, file.getName().length() - 6);

                        try {
                            classes.add(Class.forName(packageName + '.' + className));
                        } catch (ClassNotFoundException var12) {
                            var12.printStackTrace();
                        }
                    }
                }
            }

        }
    }

    public static Map<String, Object> getObjFiledValues(Object obj) {
        if (obj == null) {
            return Maps.newHashMap();
        } else {
            Field[] fields = obj.getClass().getDeclaredFields();
            Map<String, Object> fieldValues = new HashMap();
            Field[] var3 = fields;
            int var4 = fields.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                Field field = var3[var5];
                field.setAccessible(true);
                String filedName = field.getName();
                Object value = null;

                try {
                    value = field.get(obj);
                } catch (IllegalAccessException var10) {
                }

                fieldValues.put(filedName, value);
            }

            return fieldValues;
        }
    }

    public static Object getObjFiledValue(Object obj, String fieldName) throws NoSuchFieldException {
        if (obj != null && !StringUtils.isBlank(fieldName)) {
            Field field = obj.getClass().getDeclaredField(fieldName);
            Object filedValue = null;
            field.setAccessible(true);

            try {
                filedValue = field.get(obj);
            } catch (IllegalAccessException var5) {
            }

            return filedValue;
        } else {
            return null;
        }
    }
}