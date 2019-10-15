package core.common.model;

import java.io.Serializable;

/**
 * 通用的消息格式
 *
 * @author maxuefeng
 */
public class M<T> implements Serializable, Cloneable {

    // 默认成功code为1
    private final static int DEFAULT_SUCCESS_CODE = 1;

    // 默认成功msg为OK
    private final static String DEFAULT_SUCCESS_MSG = "OK";

    // 默认错误code为0
    private final static int DEFAULT_FAILURE_CODE = 0;

    // 默认错误msg为ERROR
    private final static String DEFAULT_FAILURE_MSG = "ERROR";

    // 状态码
    private int code;

    // 响应额外信息
    private String msg;

    // 附带数据
    private T data;

    // 是否成功
    private boolean success;

    public M(int code, String msg, T data, boolean success) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.success = success;
    }

    public static <T> M<T> success() {
        return success(null);
    }


    public static <T> M<T> success(T data) {
        return success(data, DEFAULT_SUCCESS_MSG);
    }

    public static <T> M<T> success(T data, String msg) {
        return new M<>(DEFAULT_SUCCESS_CODE, msg, data, true);
    }

    public static <T> M<T> failure() {
        return failure(DEFAULT_FAILURE_CODE);
    }

    public static <T> M<T> failure(String msg) {
        return failure(DEFAULT_FAILURE_CODE, msg);
    }

    public static <T> M<T> failure(int code) {
        return failure(code, DEFAULT_FAILURE_MSG);
    }

    public static <T> M<T> failure(int code, String msg) {
        return failure(code, msg, null);
    }

    public static <T> M<T> failure(int code, String msg, T data) {
        return new M<>(code, msg, data, false);
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "M{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", success=" + success +
                '}';
    }
}
