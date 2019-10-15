package core.function.predicate;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @author maxuefeng
 * @since 2019-04-28
 */
public class CollectionPredicate {


    // 集合判断
    public static Predicate<Collection> noNull = c -> c != null && c.size() > 0;

    public static BiConsumer<Collection, Consumer<Collection>> afterNoNull = (collect, after) -> {
        if (noNull.test(collect)) after.accept(collect);
    };

    /*public static BiConsumer<Collection, Consumer<Collection>> afterNull = (collect, after) -> {
        if (noNull.test(collect)) after.accept(collect);
    };*/
}
