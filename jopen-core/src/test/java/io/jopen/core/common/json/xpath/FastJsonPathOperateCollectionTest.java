package io.jopen.core.common.json.xpath;

import com.alibaba.fastjson.JSONPath;
import io.jopen.core.common.text.Worker;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author maxuefeng
 * @since 2019/10/17
 */
public class FastJsonPathOperateCollectionTest {


    public static class Entity {
        private Integer id;
        private String name;
        private Object value;

        public Entity() {}
        public Entity(Integer id, Object value) { this.id = id; this.value = value; }
        public Entity(Integer id, String name) { this.id = id; this.name = name; }
        public Entity(String name) { this.name = name; }

        public Integer getId() { return id; }
        public Object getValue() { return value; }
        public String getName() { return name; }

        public void setId(Integer id) { this.id = id; }
        public void setName(String name) { this.name = name; }
        public void setValue(Object value) { this.value = value; }
    }

    @Test
    public void testOperateCollection(){
        List<Entity> entities = new ArrayList<>();
        entities.add(new Entity("Jack"));
        entities.add(new Entity("TomDing"));

        // // 返回enties的所有名称
        List<String> names = (List<String>) JSONPath.eval(entities, "$.name");
        System.err.println(names);

        Assert.assertSame(entities.get(0).getName(), names.get(0));
        Assert.assertSame(entities.get(1).getName(), names.get(1));
    }

}
