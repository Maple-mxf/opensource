package io.jopen.springboot.plugin.mongo.template.builder;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.data.mongodb.core.query.Update;

/**
 * @author maxuefeng
 * @since 2019/11/18
 */
public class UpdateBuilder<T> extends Builder<T> {

    public static <V> UpdateBuilder<V> builderFor(@NonNull Class<V> classType) {
        return new UpdateBuilder<>(classType);
    }

    private UpdateBuilder(Class<T> targetClass) {
        this.clazz = targetClass;
    }

    private Update update = new Update();

    public UpdateBuilder<T> set(@NonNull SFunction<T, ?> sFunction, Object value) {
        update.set(super.produceValName.apply(sFunction), value);
        return this;
    }

    public Update build() {
        return this.update;
    }
}
