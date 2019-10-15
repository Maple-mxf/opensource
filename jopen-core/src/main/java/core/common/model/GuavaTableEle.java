package core.common.model;

/**
 * @author maxuefeng
 * @see com.google.common.collect.Table
 * @since 2019/8/15
 */
public class GuavaTableEle<R, C, V> {

    public R rowKey;
    public C columnKey;
    public V value;
    
    public GuavaTableEle(R rowKey, C columnKey, V value) {
        this.rowKey = rowKey;
        this.columnKey = columnKey;
        this.value = value;
    }

    public static <R, C, V> GuavaTableEle<R, C, V> of(R rowKey, C columnKey, V value) {
        return new GuavaTableEle<>(rowKey, columnKey, value);
    }
}
