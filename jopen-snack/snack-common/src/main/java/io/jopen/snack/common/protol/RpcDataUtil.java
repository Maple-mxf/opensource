package io.jopen.snack.common.protol;

import com.google.protobuf.Any;

import static io.jopen.snack.common.protol.Message.failure;
import static io.jopen.snack.common.protol.Message.success;

/**
 * @author maxuefeng
 * @since 2019/10/27
 */
public final
class RpcDataUtil {

    public static RpcData.S2C defaultSuccess() {
        return RpcData.S2C.newBuilder().setCode(success.getCode()).setErrMsg(success.getMsg()).build();
    }

    public static RpcData.S2C defaultSuccess(int updateRow) {
        return RpcData.S2C.newBuilder().setCode(success.getCode()).setErrMsg(success.getMsg()).setUpdateRow(updateRow).build();
    }

    public static RpcData.S2C defaultSuccess(Any any) {
        return RpcData.S2C.newBuilder().setCode(success.getCode()).setErrMsg(success.getMsg()).setCollectionRes(0,any).build();
    }

    public static RpcData.S2C defaultFailure() {
        return RpcData.S2C.newBuilder().setCode(failure.getCode()).setErrMsg(failure.getMsg()).build();
    }
}
