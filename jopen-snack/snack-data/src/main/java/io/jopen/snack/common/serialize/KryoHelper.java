package io.jopen.snack.common.serialize;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.JavaSerializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

/**
 * 序列化协议
 *
 * @author maxuefeng
 * @since 2019/10/23
 */
public final class KryoHelper {

    public static byte[] serialization(Object object) throws IOException {
        Kryo kryo = new Kryo();
        kryo.setReferences(false);
        kryo.register(object.getClass(), new JavaSerializer());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        Output output = new Output(baos);
        kryo.writeClassAndObject(output, object);
        output.flush();
        output.close();

        byte[] bytes = baos.toByteArray();

        baos.flush();
        baos.close();

        return bytes;
    }

    public static  <T extends Serializable> T deserialization(byte[] bytes, Class<T> clazz) throws IOException {
        Kryo kryo = new Kryo();
        kryo.setReferences(false);
        kryo.register(clazz, new JavaSerializer());
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        Input input = new Input(bais);
        T t = (T) kryo.readClassAndObject(input);
        bais.close();
        return t;
    }
}
