package io.jopen.ssh;

/**
 * @author maxuefeng
 * @since 2020/2/11
 */
public final class Response implements java.io.Serializable{

    private boolean success;

    private String errMsg;

    private Object data;

    public static Response ok() {
        Response response = new Response();
        response.setSuccess(true);
        return response;
    }

    public static Response ok(Object data) {
        Response response = new Response();
        response.setSuccess(true);
        response.setData(data);
        return response;
    }

    public static Response error(String errMsg) {
        Response response = new Response();
        response.setSuccess(false);
        response.setErrMsg(errMsg);
        return response;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
