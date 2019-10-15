package core.common.io;

import okhttp3.Response;

/**
 * Http请求组件
 *
 * @author maxuefeng
 */
public final class RequestHelper{

    public static class ResponseWrapper {

        private Response response;

        public ResponseWrapper(Response res) {
            this.response = res;
        }
    }


    /**
     * 伪装饰器模式
     */
    public static class HypocriticalResDecorator {
    }
}
