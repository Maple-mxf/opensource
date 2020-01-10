package io.jopen.apply.plugin.demo.mongo;

import io.jopen.springboot.plugin.mongo.template.builder.BaseModel;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author maxuefeng
 * @see com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper
 * @since 2020/1/9
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Data
@Document(collection = "user")
public class User extends BaseModel {

    @Id
    @Indexed
    private String id;

    private int age;

    private String name;

    private String address;

    private double weight;

    private long birth;

}
