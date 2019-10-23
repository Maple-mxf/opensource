package io.jopen.memdb.base.storage;

import io.jopen.core.common.text.Worker;
import org.junit.Test;

import java.util.List;

/**
 * @author maxuefeng
 * @since 2019/10/23
 */
public class MemdbTemplateImplTest {

    @Test
    public void testGetInstance() throws Throwable {

        // 创建客户端单例
        MemdbTemplateImpl memTemplateInstance = new MemdbTemplateImpl.Builder().switchDB("default").build();

        Student student = new Student();
        String id = Worker.id();
        student.setId(id);
        student.setName("Jack");

        // 保存数据
        Boolean aBoolean = memTemplateInstance.save(student);

        // 查询/ 修改 示例
        IntermediateExpression<Student> expression = IntermediateExpression.buildFor(Student.class);
        expression.eq("id", id);
        expression.le("age", 20);


        // 批量查询
        List<Student> students = memTemplateInstance.select().input(expression).selectList();

        // 批量更新
        memTemplateInstance.update().input(expression).updateBatch();

        // 批量删除
        memTemplateInstance.delete().input(expression).delete();

        // 批量插入



    }
}
