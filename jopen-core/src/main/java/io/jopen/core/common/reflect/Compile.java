package io.jopen.core.common.reflect;

import javax.tools.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.StringWriter;
import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

/**
 * @author maxuefeng
 */
class Compile {

    static Class<?> compile(String className, String content, CompileOptions compileOptions) {

        MethodHandles.Lookup lookup = MethodHandles.lookup();

        ClassLoader cl = lookup.lookupClass().getClassLoader();

        try {
            return cl.loadClass(className);

        } catch (ClassNotFoundException ignore) {

            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

            try {
                ClassFileManager fileManager = new ClassFileManager(compiler.getStandardFileManager(null, null, null));

                List<CharSequenceJavaFileObject> files = new ArrayList<>();
                files.add(new CharSequenceJavaFileObject(className, content));
                StringWriter out = new StringWriter();

                List<String> options = new ArrayList<>(compileOptions.options);

                if (!options.contains("-classpath")) {
                    StringBuilder classpath = new StringBuilder();
                    String separator = System.getProperty("path.separator");
                    String prop = System.getProperty("java.class.path");

                    if (prop != null && !"".equals(prop))
                        classpath.append(prop);

                    if (cl instanceof URLClassLoader) {
                        for (URL url : ((URLClassLoader) cl).getURLs()) {
                            if (classpath.length() > 0)
                                classpath.append(separator);

                            if ("file".equals(url.getProtocol()))
                                classpath.append(new File(url.getFile()));
                        }
                    }

                    options.addAll(Arrays.asList("-classpath", classpath.toString()));
                }

                JavaCompiler.CompilationTask task = compiler.getTask(out, fileManager, null, options, null, files);

                if (!compileOptions.processors.isEmpty())
                    task.setProcessors(compileOptions.processors);

                task.call();

                if (fileManager.isEmpty())
                    throw new ReflectException("Compilation error: " + out);

                Class<?> result = null;

                // This works if we have private-access to the interfaces in the class hierarchy
                if (Reflect.CACHED_LOOKUP_CONSTRUCTOR != null) {
                    result = fileManager.loadAndReturnMainClass(className,
                            (name, bytes) -> Reflect.on(cl).call("defineClass", name, bytes, 0, bytes.length).get());
                }


                return result;
            } catch (ReflectException e) {
                throw e;
            } catch (Exception e) {
                throw new ReflectException("Error while compiling " + className, e);
            }
        }
    }


    /**
     *
     */
    static final class JavaFileObject extends SimpleJavaFileObject {

        final ByteArrayOutputStream os = new ByteArrayOutputStream();

        JavaFileObject(String name, Kind kind) {
            super(URI.create("string:///" + name.replace('.', '/') + kind.extension), kind);
        }

        byte[] getBytes() {
            return os.toByteArray();
        }

        @Override
        public OutputStream openOutputStream() {
            return os;
        }
    }


    static final class ClassFileManager extends ForwardingJavaFileManager<StandardJavaFileManager> {
        private final Map<String, JavaFileObject> fileObjectMap;
        private Map<String, byte[]> classes;

        ClassFileManager(StandardJavaFileManager standardManager) {
            super(standardManager);

            fileObjectMap = new HashMap<>();

        }

        @Override
        public JavaFileObject getJavaFileForOutput(
                Location location,
                String className,
                JavaFileObject.Kind kind,
                FileObject sibling
        ) {
            JavaFileObject result = new JavaFileObject(className, kind);
            fileObjectMap.put(className, result);
            return result;
        }

        boolean isEmpty() {
            return fileObjectMap.isEmpty();
        }

        Map<String, byte[]> classes() {
            if (classes == null) {
                classes = new HashMap<>();

                for (Map.Entry<String, JavaFileObject> entry : fileObjectMap.entrySet())
                    classes.put(entry.getKey(), entry.getValue().getBytes());
            }

            return classes;
        }

        Class<?> loadAndReturnMainClass(String mainClassName, ThrowingBiFunction<String, byte[], Class<?>> definer) throws Exception {

            Class<?> result = null;

            for (Map.Entry<String, byte[]> entry : classes().entrySet()) {
                Class<?> c = definer.apply(entry.getKey(), entry.getValue());
                if (mainClassName.equals(entry.getKey()))
                    result = c;
            }

            return result;
        }
    }

    @FunctionalInterface
    interface ThrowingBiFunction<T, U, R> {
        R apply(T t, U u) throws Exception;
    }

    static final class CharSequenceJavaFileObject extends SimpleJavaFileObject {
        final CharSequence content;

        public CharSequenceJavaFileObject(String className, CharSequence content) {
            super(URI.create("string:///" + className.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
            this.content = content;
        }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
            return content;
        }
    }
}
