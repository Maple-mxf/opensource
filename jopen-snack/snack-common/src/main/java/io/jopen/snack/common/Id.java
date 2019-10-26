package io.jopen.snack.common;

import com.google.common.base.Preconditions;

import java.util.Map;
import java.util.Optional;

/**
 * Id包装器
 *
 * @author maxuefeng
 * @since 2019/10/24
 */
public final
class Id {

    private Map<String, Object> ids;

    private Id(Map<String, Object> ids) {
        this.ids = ids;
    }

    /**
     * 创建主键
     *
     * @param ids key表示主键的列的名称  value表示主键的值
     * @return Id instance
     */
    public static Id of(Map<String, Object> ids) {
        Preconditions.checkNotNull(ids);
        return new Id(ids);
    }

    public Map<String, Object> getId() {
        return this.ids;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj instanceof Id) {
            Id otherId = (Id) obj;
            Map<String, Object> otherIds = otherId.getId();
            return otherIds.equals(this.ids);
        }
        return false;
    }

    public Boolean isNull() {
        Optional<Map.Entry<String, Object>> optional = this.ids.entrySet().parallelStream().filter(entry -> entry.getValue() == null).findAny();
        return optional.isPresent();
    }

    public int size() {
        return this.ids.size();
    }


}
