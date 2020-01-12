package io.jopen.snack.common.protol;

/**
 * @author maxuefeng
 * @since 2019/10/26
 */
public enum Message {

    success(1, "success"),
    failure(0, "unknow error");

    private int code;
    private String msg;

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

    @Override
    public String toString() {
        return "Message{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }

    Message(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
