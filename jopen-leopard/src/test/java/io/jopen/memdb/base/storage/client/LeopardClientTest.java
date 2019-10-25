package io.jopen.memdb.base.storage.client;

import io.jopen.core.common.text.Worker;
import io.jopen.memdb.base.storage.Student;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

/**
 * @author maxuefeng
 * @since 2019/10/24
 */
public class LeopardClientTest {

    // 创建客户端
    private final LeopardClient leopardClient = new LeopardClient.Builder().startDBServer().build();

    // 保存数据
    @Before
    public void before() {

        // 切换数据库
        leopardClient.switchDB("default");

        Student student = new Student();

        student.setAge(20);
        student.setId(Worker.id());
        student.setName("Jack");

        int save = leopardClient.input(student).save().execute();
        System.err.println(save);
    }

    @Test
    public void testSelect() throws Throwable {

        // 构建查询条件
        IntermediateExpression<Student> expression = IntermediateExpression.buildFor(Student.class).le("age", 30);

        // 得到结果
        Collection<Student> collection = leopardClient
                // 输入条件表达式
                .input(expression)
                // 表示需要进行查询操作
                .select()
                // 执行
                .execute();

        System.err.println(collection);
    }

    @Test
    public void select() throws Throwable {
        IntermediateExpression<Student> expression = IntermediateExpression.buildFor(Student.class).eq("age", 20);
        Collection<Student> collection = leopardClient.input(expression).select().execute();
        System.err.println(collection);
    }

    @Test
    public void delete() {
        //
        IntermediateExpression<Student> expression = IntermediateExpression.buildFor(Student.class).eq("age", 20);
        int execute = leopardClient.input(expression).delete().execute();
        System.err.println(execute);
    }

    @Test
    public void update() {
        IntermediateExpression<Student> expression = IntermediateExpression.buildFor(Student.class).eq("age", 20);
        int update = leopardClient.input(expression).update().set("age", 90).execute();
        System.err.println(update);
    }

    @After
    public void after() throws Throwable {
        // query all data
        IntermediateExpression<Student> expression = IntermediateExpression.buildFor(Student.class);
        Collection<Student> collection = leopardClient.input(expression).select().execute();
        System.err.println(String.format("all data is   %s  ", collection));
    }


}
