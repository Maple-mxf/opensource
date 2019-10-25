package io.jopen.memdb.base.storage.client;

import io.jopen.memdb.base.storage.Student;
import org.junit.Test;

import java.util.Collection;

/**
 * @author maxuefeng
 * @since 2019/10/24
 */
public class MemDBClientInstanceTest {

    // 创建客户端
    private final MemDBClientInstance memDBClientInstance = new MemDBClientInstance.Builder().startDBServer().switchDB("default").build();

    @Test
    public void testUpdate() {

        // 构建查询条件
        IntermediateExpression<Student> expression = IntermediateExpression.buildFor(Student.class).eq("id", "123");

        // 更新数据
        int result = memDBClientInstance
                // 输入条件表达式
                .input(expression)
                // 表示需要进行更新操作
                .update()
                // 设定需要更改的字段
                .set("name", "Jackma")
                // 进行执行
                .execute();
    }

    @Test
    public void testSelect() throws Throwable {

        // 构建查询条件
        IntermediateExpression<Student> expression = IntermediateExpression.buildFor(Student.class).le("age", 10);

        // 得到结果
        Collection<Student> collection = memDBClientInstance
                // 输入条件表达式
                .input(expression)
                // 表示需要进行查询操作
                .select()
                // 执行
                .execute();
    }

    // TODO  delete poeration will send java beans
    @Test
    public void testDelete() {
        // IntermediateExpression<Student> expression = IntermediateExpression.buildFor(Student.class).le("age", 10);
        // memDBClientInstance.input()

        // 全删除
        // memDBClientInstance.input().delete().

    }


}
