package io.jopen.dataframe;

import java.util.Collection;
import java.util.Map;

/**
 * @author maxuefeng
 * @since 2019/12/22
 */
public class DefaultDataFrame implements DataFrame {


    // 数组  对象
    public DefaultDataFrame(Collection<Map<String, Object>> collection) {
        // 如果数据是基本类型
    }

    /**
     * @param dsl 字符串
     * @return
     */
    @Override
    public DataFrame get(String dsl) {
        return null;
    }

    @Override
    public DataFrame remove(String dsl) {
        return null;
    }

    @Override
    public DataFrame map(String dsl) {
        return null;
    }

    @Override
    public DataFrame filter(String dsl) {
        return null;
    }

    @Override
    public DataFrame count(String dsl) {
        return null;
    }
}
