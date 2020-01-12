package io.jopen.orm.hbase.translator;

public interface EntityResolver {
    String resolve(Class<?> entityClass);
}
