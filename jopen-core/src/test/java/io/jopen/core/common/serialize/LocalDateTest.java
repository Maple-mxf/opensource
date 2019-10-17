package io.jopen.core.common.serialize;

import org.junit.Test;

import java.io.*;
import java.time.LocalDate;
import java.util.Optional;

/**
 * @author maxuefeng
 * @see LocalDate
 * @see java.time.LocalDateTime
 */
public class LocalDateTest {

    /**
     * 测试LocalDate和LocalDateTime序列化
     */
    @Test
    public void testSerializeObject2File() {

        FileOutputStream fos = null;

        ObjectOutputStream oos = null;

        try {

            fos = new FileOutputStream("tmp/localDate.txt");
            oos = new ObjectOutputStream(fos);

            // 序列化LocalDateTime对象
            oos.writeObject(LocalDate.now());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            // close stream
            Optional.ofNullable(oos).ifPresent(o -> {
                try {
                    o.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            Optional.ofNullable(fos).ifPresent(o -> {
                try {
                    o.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    /**
     * 反序列化
     */
    @Test
    public void inSerializeFile2Object() {

        FileInputStream fis = null;

        ObjectInputStream ois = null;

        try {
            fis = new FileInputStream("tmp/localDate.txt");

            ois = new ObjectInputStream(fis);

            LocalDate object = (LocalDate) ois.readObject();

            System.err.println(object);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // close stream
            Optional.ofNullable(ois).ifPresent(o -> {
                try {
                    o.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            Optional.ofNullable(fis).ifPresent(o -> {
                try {
                    o.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
