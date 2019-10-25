package io.jopen.leopard.base.storage.util;

import io.jopen.leopard.base.storage.serialize.SFunction;
import io.jopen.leopard.base.storage.serialize.SerializedLambda;
import io.jopen.leopard.base.storage.Student;
import io.jopen.leopard.base.storage.serialize.LambdaHelper;
import org.junit.Test;

/**
 * <p>{@link LambdaHelper}</p>
 *
 * @author maxuefeng
 * @since 2019/10/25
 */
public class LambdaHelperTest {

    @Test
    public void simpleTest() {

        SFunction<Student, Integer> function = Student::getAge;

        SerializedLambda lambda = LambdaHelper.resolve(function);

        System.err.println(lambda);
    }
}
