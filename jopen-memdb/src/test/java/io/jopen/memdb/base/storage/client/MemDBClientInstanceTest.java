package io.jopen.memdb.base.storage.client;

import io.jopen.memdb.base.storage.Student;
import org.junit.Test;

/**
 * @author maxuefeng
 * @since 2019/10/24
 */
public class MemDBClientInstanceTest {

    @Test
    public void testUpdate() {

        // 创建客户端
        MemDBClientInstance memDBClientInstance = new MemDBClientInstance.Builder().startDBServer().switchDB("default").build();

        // 构建查询条件
        IntermediateExpression<Student> expression = IntermediateExpression.buildFor(Student.class).eq("id","123");

        // 更新数据
        memDBClientInstance
                // 输入条件表达式
                .input(expression)
                // 表示需要进行更新操作
                .update()
                // 设定需要更改的字段
                .set("name","Jackma")
                // 进行执行
                .execute();
    }
}
