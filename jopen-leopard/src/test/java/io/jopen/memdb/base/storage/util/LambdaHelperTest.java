package io.jopen.memdb.base.storage.util;

import io.jopen.memdb.base.storage.Student;
import io.jopen.memdb.base.storage.serialize.LambdaHelper;
import io.jopen.memdb.base.storage.serialize.SFunction;
import io.jopen.memdb.base.storage.serialize.SerializedLambda;
import org.junit.Test;

/**
 * <p>{@link io.jopen.memdb.base.storage.serialize.LambdaHelper}</p>
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
