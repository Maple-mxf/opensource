package io.jopen.springboot.plugin.mongo.template.builder;

import io.jopen.springboot.plugin.mongo.code.generator.test.User;
import org.springframework.data.mongodb.core.query.Update;

/**
 * @author maxuefeng
 * @since 2020/2/9
 */
public class UpdateBuilderTest {

    public void testBuildUpdate(){
        Update update = UpdateBuilder.builderFor(User.class).set(User::getAge, 20).build();
    }
}
