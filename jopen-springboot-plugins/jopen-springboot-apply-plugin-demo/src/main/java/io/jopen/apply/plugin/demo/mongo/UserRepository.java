package io.jopen.apply.plugin.demo.mongo;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import io.jopen.springboot.plugin.mongo.template.builder.AggregationBuilder;
import io.jopen.springboot.plugin.mongo.template.builder.BaseModel;
import io.jopen.springboot.plugin.mongo.template.builder.QueryBuilder;
import io.jopen.springboot.plugin.mongo.template.builder.UpdateBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author maxuefeng
 * @since 2020/1/9
 */
@Component
public class UserRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    public Collection<User> getUserListByQueryBuilder() {
        // 查询年龄大于等于18的用户
        Query query = QueryBuilder.builderFor(User.class).gte(User::getAge, 18).build();
        return mongoTemplate.find(query, User.class);
    }

    public Collection<User> getUserListByExcludeSomeFields() {
        /*只查询部分字段 */
        Query query = QueryBuilder.builderFor(User.class)
                .eq(User::getId,"1")
                .excludeFields(User::getBirth, User::getName).build();
        return mongoTemplate.find(query, User.class);
    }

    public List<Map> getAggUserList() {
        // 根据用户年龄和地址进行分组  并且对于年龄进行求和
        Aggregation aggregation = AggregationBuilder.builderFor(User.class).groupByAndSumMultiFieldSum(
                ImmutableList.of(User::getAddress, User::getAge),
                /*Key表示求和的字段  value表示给当前这个字段起个别名*/
                ImmutableMap.of(User::getAge, "all_age", User::getBirth, "all_birth")
        ).build();

        return mongoTemplate.aggregate(aggregation, BaseModel.getCollectionName(User.class), Map.class).getMappedResults();
    }

    public void updateUserAge(int age){
        Update update = UpdateBuilder.builderFor(User.class).set(User::getAge, age).build();
        Query query = QueryBuilder.builderFor(User.class).eq(User::getId, "1").build();
        mongoTemplate.updateFirst(query,update,User.class);
    }
}
