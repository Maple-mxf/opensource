package io.jopen.orm.hbase.translator;

public class SimpleEntityResolver implements EntityResolver {

    @Override
    public String resolve(Class<?> entityClass) {
        return entityClass.getSimpleName();
    }

}
