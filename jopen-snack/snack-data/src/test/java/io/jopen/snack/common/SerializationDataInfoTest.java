package io.jopen.snack.common;

import com.google.protobuf.Any;
import com.google.protobuf.ByteString;
import io.jopen.snack.common.protol.SerializationDataInfo;
import io.jopen.snack.common.serialize.KryoHelper;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @author maxuefeng
 * @since 2019/10/26
 */
public class SerializationDataInfoTest {

    // 将函数式接口序列化
    private Condition condition = (Condition) Objects::nonNull;

    /**
     * @throws IOException
     * @see com.google.protobuf.Api
     */
    @Test
    public void testBuildCondition() throws IOException, NoSuchMethodException {

        // 创建expression实体类
        Any any = Any.newBuilder().setValue(ByteString.copyFrom(KryoHelper.serialization(condition))).build();
        SerializationDataInfo.RpcExpression rpcExpression =
                SerializationDataInfo.RpcExpression.newBuilder()
                        .addConditions(any)
                        .build();
        byte[] originBytes = rpcExpression.toByteArray();


        SerializationDataInfo.RpcExpression afterExpression = SerializationDataInfo.RpcExpression.parseFrom(originBytes);
        List<Any> conditionsList = afterExpression.getConditionsList();

        for (Any otherAny : conditionsList) {
            byte[] bytes = otherAny.getValue().toByteArray();
            System.err.println(bytes.length);
            Condition deserialization = KryoHelper.deserialization(bytes, Condition.class);
            System.err.println(deserialization.test(null));
        }

    }
}
