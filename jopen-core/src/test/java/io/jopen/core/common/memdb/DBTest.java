package io.jopen.core.common.memdb;

import io.jopen.core.common.text.Worker;
import org.junit.Test;

import java.io.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author maxuefeng
 * @since 2019/10/22
 */
public class DBTest {

    Database database = DBA.DBA.use("test");

    @Test
    public void showTables() {
        ConcurrentHashMap<String, JavaModelTabale> tables = DBA.DBA.use("test").showTables();
        tables.forEach((k, v) -> System.err.println(k + v));
    }

    @Test
    public void testCreateTable() throws Throwable {

        // 创建表格
        database.createTable(Student.class);


        Student student = new Student();
        student.id = Worker.id();
        student.name = "Jack Ma";

        JavaModelTabale<Student> table = database.getTable(Student.class);
        table.add(student);

        // System.err.println(table);

        Student one = table.queryOne(student);
        System.err.println(one.id + "  " + one.getName());

        table.delete(student);

        System.err.println(table);
    }

    @Test
    public void testSerializeObject() throws IOException {
        // 创建表格
        database.createTable(Student.class);

        Student student = new Student();
        student.id = Worker.id();
        student.name = "Jack Ma";

        JavaModelTabale<Student> table = database.getTable(Student.class);
        table.add(student);

        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("./objectFile.obj"));
        out.writeObject(table);
        out.close();
    }

    @Test
    public void deSerializeObject() throws IOException, ClassNotFoundException {
        //反序列化对象
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("./objectFile.obj"));

        Object object = in.readObject();
        System.err.println(object);
    }
}
