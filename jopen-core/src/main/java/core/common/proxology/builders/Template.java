package core.common.proxology.builders;

import java.util.function.Supplier;

/**
 * @author maxuefeng
 */
public interface Template<T, B extends Supplier<T>> extends Supplier<T> {
    
    /**
     * @param templateClass 目标Class
     * @param <V>           V
     * @param <B>           B
     * @param <T>           T
     * @return Value
     */
    static <V, B extends Supplier<V>, T extends Template<V, B>> B builderFor(Class<T> templateClass) {
        return TemplateValueStore.createBuilder(templateClass);
    }
}
