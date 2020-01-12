package io.jopen.orm.hbase.hbase.translator;

import com.google.code.morphia.annotations.Entity;
import io.jopen.orm.hbase.translator.EntityResolver;
import org.apache.commons.lang3.StringUtils;

public class MorphiaEntityResolver implements EntityResolver {

    @Override
    public String resolve(Class<?> entityClass) {
        Entity entity = entityClass.getAnnotation(Entity.class);
        String mappedName = entityClass.getSimpleName();
        if (entity != null && StringUtils.isNotBlank(entity.value())) {
            mappedName = entity.value();
        }
        return mappedName;
    }

}
