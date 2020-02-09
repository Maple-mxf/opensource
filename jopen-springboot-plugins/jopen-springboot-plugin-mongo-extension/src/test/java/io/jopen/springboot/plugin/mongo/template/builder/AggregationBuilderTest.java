package io.jopen.springboot.plugin.mongo.template.builder;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import io.jopen.springboot.plugin.mongo.code.generator.test.User;
import org.junit.Test;

/**
 * @author maxuefeng
 * @since 2020/2/9
 */
public class AggregationBuilderTest {

    @Test
    public void testBuildAggregation(){
        AggregationBuilder.builderFor(User.class)
                .groupByAndSingleFieldSum(User::getAge,"sum_age", ImmutableList.of(User::getId))
                .build();
    }
}
