package io.jopen.core.common.memdb;

/**
 * @author maxuefeng
 * @since 2019/10/22
 */
@Deprecated
public class DataStoreClient {

    // 客户端登陆
    private DataStoreClient() {
    }

    public static final DataStoreClient STORE_CLIENT = new DataStoreClient();
}
