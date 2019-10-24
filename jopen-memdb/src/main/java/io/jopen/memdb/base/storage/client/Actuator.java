package io.jopen.memdb.base.storage.client;

import java.util.HashMap;

/**
 * 执行器  将中间构建的表达式和底层存储table进行对接
 * <p>{@link io.jopen.memdb.base.storage.server.RowStoreTable}</p>
 *
 * @author maxuefeng
 * @since 2019/10/24
 */
final
class Actuator {

    @Deprecated
    void execute(Builder.Carrier carrier) {
        Builder builder = carrier.getBuilder();

        // query operation
        if (carrier.getClass().equals(Builder.Select.class)) {

        }
        // delete operation
        else if (carrier.getClass().equals(Builder.Delete.class)) {

        }
        // update operation
        else if (carrier.getClass().equals(Builder.Update.class)) {

        }
        // save operation
        else if (carrier.getClass().equals(Builder.Save.class)) {

        }
    }

    void update(Builder.Update update) {
        HashMap<String, Object> body = update.getBody();
        // 获取对应table对象
        Class targetClass = update.getBuilder().getExpression().getTargetClass();


    }
}
