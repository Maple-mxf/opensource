package io.jopen.orm.hbase.translator;

import io.jopen.orm.hbase.mapper.EntityPropertyBinding;

import java.util.List;
import java.util.Map;

public interface PropertyResolver {

    public String resolve(String entityFieldName, Class<?> entityClass);

    public List<String> resolveEntityMappingPropertyNames(List<String> entityFieldNames, Class<?> entityClass);

    public EntityPropertyBinding resolveEntityPropertyBindingByStoreMappingName(String mappingName, Class<?> clz);

    public EntityPropertyBinding resolveEntityPropertyBindingByEntityFieldName(String entityFieldName, Class<?> entityClass);

    public <T> Map<String, EntityPropertyBinding> getStoreFieldNamePropertyBindingMap(Class<T> clz);

    public <T> Map<String, EntityPropertyBinding> getEntityPropertyNamePropertyBindingMap(Class<T> clz);

}
