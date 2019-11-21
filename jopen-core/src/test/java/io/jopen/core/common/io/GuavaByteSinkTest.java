package io.jopen.core.common.io;

import com.google.common.io.ByteSink;
import com.google.common.io.FileWriteMode;
import com.google.common.io.Files;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author maxuefeng
 * @since 2019/11/1
 */
public class GuavaByteSinkTest {

    String path = "E:\\java-workplace\\opensource\\jopen-core\\src\\test\\java\\io\\jopen\\core\\common\\io\\test.rdb";

    @Test
    public void testAppendFile() throws IOException {

        Collection<Person> c1 = new ArrayList<>();
        c1.add(new Person("s1", 100));

        ByteSink byteSink = Files.asByteSink(new File(path), FileWriteMode.APPEND);
        byteSink.write(pojoToBytes(c1));
    }


    @Test
    public void testReadFile() throws IOException {
        Collection<Person> c1 = (Collection<Person>) bytesToPojo(Files.asByteSource(new File(path)).read());
        System.err.println(c1);
    }


    public byte[] pojoToBytes(Object object) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            ObjectOutput out;
            out = new ObjectOutputStream(bos);
            out.writeObject(object);
            out.flush();
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Object bytesToPojo(byte[] bytes) {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        try (ObjectInput in = new ObjectInputStream(bis)) {
            return in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        // ignore close io.jopen.springboot.encryption.exception
    }
}
