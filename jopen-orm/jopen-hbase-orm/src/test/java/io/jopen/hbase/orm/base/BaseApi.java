package io.jopen.hbase.orm.base;


import com.google.common.collect.ImmutableList;
import io.jopen.orm.hbase.api.ReflectHelper;
import io.jopen.orm.hbase.hbase.PhoenixHBaseDataStoreApiImpl;
import io.jopen.orm.hbase.hbase.mapper.PhoenixProjectedResultMapper;
import io.jopen.orm.hbase.hbase.query.PhoenixHBaseQueryExecutor;
import io.jopen.orm.hbase.hbase.translator.PhoenixHBaseQueryTranslator;
import io.jopen.orm.hbase.mapper.EntityPropertiesMappingContext;
import io.jopen.orm.hbase.mapper.EntityPropertiesResolver;
import io.jopen.orm.hbase.query.QuerySelect;
import io.jopen.orm.hbase.query.QueryUpdate;
import io.jopen.orm.hbase.query.builder.QueryBuilder;
import io.jopen.orm.hbase.query.builder.QueryUpdateBuilder;
import io.jopen.orm.hbase.query.criterion.Restrictions;
import io.jopen.orm.hbase.query.criterion.projection.CountProjection;
import io.jopen.orm.hbase.query.criterion.projection.GroupProjection;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author maxuefeng
 * @since 2020/1/12
 */
public class BaseApi {

    // 指定包下的实体类
    private List<String> getClasses(String packageName) {
        List<Class<?>> classes = ReflectHelper.getClasses(packageName);
        return classes.parallelStream().map(Class::getName).collect(Collectors.toList());
    }

    class User {
        String name;
        int age;
    }

    @Test
    public void test0() throws Exception {

        // 配置驱动
        // TODO  需要设置实体类的包名  如果在多个包下  需要开发者自行制定类的名称
        EntityPropertiesMappingContext context = new EntityPropertiesMappingContext(getClasses(""));
        EntityPropertiesResolver resolver = new EntityPropertiesResolver(context);
        PhoenixHBaseQueryTranslator translator = new PhoenixHBaseQueryTranslator(resolver);
        PhoenixProjectedResultMapper resultMapper = new PhoenixProjectedResultMapper(resolver);
        PhoenixHBaseQueryExecutor queryExecutor = new PhoenixHBaseQueryExecutor(translator, resultMapper);
        PhoenixHBaseDataStoreApiImpl dataStoreApi = new PhoenixHBaseDataStoreApiImpl("jdbc:phoenix:thin:url=http://host:8765;serialization=PROTOBUF", queryExecutor);


        // 项目的入口初始化完毕
        dataStoreApi.save(new User());

        // 构建简单查询
        QuerySelect<User, User> query = QueryBuilder.builderFor(User.class).select()
                // 添加查询条件
                .add(Restrictions.eq("name", "maxuefeng"))
                .build();
        User user = dataStoreApi.findOne(query);

        // 构建复杂聚合查询
        QuerySelect<User, User> aggQuery = QueryBuilder.builderFor(User.class).select()
                // 根据姓名进行分组
                .addGroupCriterion(new GroupProjection("name"))
                // 根据年龄进行求和
                .addProjection(new CountProjection("age"))
                .build();

        Iterable<User> groupByAll = dataStoreApi.findAll(aggQuery);

        // 构建修改操作
        User user1 = new User();
        QueryUpdate<User> queryUpdate = QueryUpdateBuilder.builderFor(user1).update(ImmutableList.of("id", "name", "age")).build();
        QueryUpdate<User> updateResult = dataStoreApi.save(queryUpdate);

        // 构建数据保存操作
        User saveResult = dataStoreApi.save(user1);

    }
}
