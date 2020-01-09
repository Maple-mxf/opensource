package io.jopen.springboot.plugin.mongo.template.builder;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Collection;

/**
 * {@link com.mongodb.QueryBuilder}
 * {@link IQuery}
 * {@link SFunction} 利用方法引用的形式进行参数传递
 * {@link Query}
 * {@link Criteria}
 */
public class QueryBuilder<T> extends Builder<T> {

    private IQuery query = new IQuery();

    public QueryBuilder(Class<T> targetClass) {
        this(targetClass, null);
    }

    public QueryBuilder(Class<T> targetClass, MongoTemplate mongoTemplate) {
        this.clazz = targetClass;
        this.mongoTemplate = mongoTemplate;
    }


    public static <V> QueryBuilder<V> builderFor(@NonNull Class<V> classType) {
        return new QueryBuilder<>(classType);
    }

    public QueryBuilder<T> eq(SFunction<T, ?> sFunction, Object object) {
        String fieldName = produceValName.apply(sFunction);
        query.addCriteria(Criteria.where(fieldName).is(object));
        return this;
    }

    public QueryBuilder<T> gte(SFunction<T, ?> sFunction, Comparable<?> object) {
        String fieldName = produceValName.apply(sFunction);
        query.addCriteria(Criteria.where(fieldName).gte(object));
        return this;
    }

    public QueryBuilder<T> between(@NonNull SFunction<T, ?> sFunction, @NonNull Comparable<?> start, @NonNull Comparable<?> end) {
        String fieldName = produceValName.apply(sFunction);
        query.addCriteria(Criteria.where(fieldName).gte(start).lte(end));
        return this;
    }

    @SafeVarargs
    public final QueryBuilder<T> orderByDesc(@NonNull SFunction<T, ?>... sFunctions) {
        for (SFunction<T, ?> sFunction : sFunctions) {
            String fieldName = produceValName.apply(sFunction);
            query.with(Sort.by(Sort.Order.desc(fieldName)));
        }
        return this;
    }


    @SafeVarargs
    public final QueryBuilder<T> orderByAsc(@NonNull SFunction<T, ?>... sFunctions) {
        for (SFunction<T, ?> sFunction : sFunctions) {
            String fieldName = produceValName.apply(sFunction);
            query.with(Sort.by(Sort.Order.asc(fieldName)));
        }
        return this;
    }

    public final QueryBuilder<T> in(@NonNull SFunction<T, ?> sFunction,
                                    @NonNull Object... objects) {
        String fieldName = produceValName.apply(sFunction);
        query.addCriteria(Criteria.where(fieldName).in(objects));
        return this;
    }

    public final QueryBuilder<T> in(@NonNull SFunction<T, ?> sFunction,
                                    @NonNull Collection<Object> objects) {
        String fieldName = produceValName.apply(sFunction);
        query.addCriteria(Criteria.where(fieldName).in(objects));
        return this;
    }


    public final QueryBuilder<T> limit(int limit) {
        if (limit <= 0) {
            throw new RuntimeException("limit参数异常");
        }
        query.limit(limit);
        return this;
    }

    public QueryBuilder<T> like(@NonNull SFunction<T, ?> sFunction, String text) {
        String fieldName = produceValName.apply(sFunction);
        query.addCriteria(Criteria.where(fieldName).alike(Example.of(text)));
        return this;
    }

    @SafeVarargs
    public final QueryBuilder<T> excludeFields(@NonNull SFunction<T, ?>... sFunctions) {
        for (SFunction<T, ?> sFunction : sFunctions) {
            this.query.excludeField(produceValName.apply(sFunction));
        }
        return this;
    }

    @SafeVarargs
    public final QueryBuilder<T> includeFields(@NonNull SFunction<T, ?>... sFunctions) {
        for (SFunction<T, ?> sFunction : sFunctions) {
            this.query.includeField(produceValName.apply(sFunction));
        }
        return this;
    }

    public Query build() {
        return this.query;
    }
}
