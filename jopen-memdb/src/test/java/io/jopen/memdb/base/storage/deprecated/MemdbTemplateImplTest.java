package io.jopen.memdb.base.storage.deprecated;

import io.jopen.core.common.text.Worker;
import io.jopen.memdb.base.storage.Student;
import org.junit.Before;
import org.junit.Test;

/**
 * @author maxuefeng
 * @since 2019/10/23
 */
@Deprecated
public class MemdbTemplateImplTest {


    private Student student = new Student();

    @Before
    public void before() {
        String id = Worker.id();
        student.setId(id);
        student.setName("Jack");
        student.setAge(10);
    }

    @Test
    public void testGetInstance() throws Throwable {

        // 创建客户端单例
        MemdbTemplateImpl memTemplateInstance = MemdbTemplateImpl.Builder.startDBServer().switchDB("default").build();

        // 保存数据
        Boolean aBoolean = memTemplateInstance.save(student);
        System.err.println(aBoolean);
    }

    @Test
    public void testQueryByCondition() {
        // MemdbTemplateImpl memTemplateInstance = new MemdbTemplateImpl.QueryBuilder().switchDB("default").build();
    }


}
