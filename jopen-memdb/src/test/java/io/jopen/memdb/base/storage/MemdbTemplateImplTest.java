package io.jopen.memdb.base.storage;

import io.jopen.core.common.text.Worker;
import org.junit.Test;

/**
 * @author maxuefeng
 * @since 2019/10/23
 */
public class MemdbTemplateImplTest {

    @Test
    public void testGetInstance() throws Throwable {

        // 创建单例
        MemdbTemplateImpl memTemplateInstance = new MemdbTemplateImpl.Builder().switchDB("default").build();

        Student student = new Student();

        String id = Worker.id();

        student.setId(Worker.id());
        student.setName("Jack");

        Boolean aBoolean = memTemplateInstance.save(student);

        System.err.println(aBoolean);

        // 查询示例
        IntermediateExpression<Student> expression = IntermediateExpression.buildFor(Student.class);
        expression.eq("id", id);

        // 条件构造
        /*memTemplateInstance.selectCollection().input(expression).selectList();
        memTemplateInstance.update().execute(expression);
        memTemplateInstance.delete().execute(expression);*/

        // 无需条件构造
        memTemplateInstance.save(student);
    }
}
