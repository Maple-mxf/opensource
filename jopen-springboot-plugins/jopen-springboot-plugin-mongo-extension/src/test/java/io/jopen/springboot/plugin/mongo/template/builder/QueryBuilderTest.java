package io.jopen.springboot.plugin.mongo.template.builder;

import io.jopen.springboot.plugin.mongo.code.generator.test.User;
import org.springframework.data.mongodb.core.query.Query;

/**
 * @author maxuefeng
 * @since 2020/2/9
 */
public class QueryBuilderTest {

    public void testBuildQuery(){
        Query query = QueryBuilder.builderFor(User.class)
                .eq(User::getId, 1)
                .gte(User::getAge, 20).build();
    }

}
