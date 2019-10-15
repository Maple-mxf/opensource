package core.common.collection;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author maxuefeng
 */
public class QList {


    /**
     * @param objects
     * @param <T>
     * @return
     */
    @SafeVarargs
    public static <T> java.util.List<T> of(T... objects) {
        return new ArrayList<>(Arrays.asList(objects));
    }
}
