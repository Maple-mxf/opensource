package io.jopen.memdb.base.serialize;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.JavaSerializer;
import io.jopen.core.common.text.Worker;
import io.jopen.memdb.base.storage.Student;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author maxuefeng
 * @since 2019/10/23
 */
public class KryoTest {

    private Student student = new Student();

    @Before
    public void before() {
        String id = Worker.id();
        student.setId(id);
        student.setName("Jack");
        student.setAge(10);
    }

    @Test
    public void simpleTestAPI() {

        Kryo kryo = new Kryo();
        kryo.setReferences(false);
        kryo.register(student.getClass(), new JavaSerializer());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Output output = new Output(baos);
        kryo.writeClassAndObject(output, student);
        output.flush();
        output.close();

        // 转换为byte数组
        byte[] bytes = baos.toByteArray();
    }

    @Test
    public void testBase64(){
        System.err.println(Base64.getEncoder().encodeToString(Student.class.getName().getBytes(StandardCharsets.UTF_8)));
        // Hashing.sha256().newHasher().putString()
    }
}
