package core.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author maxuefeng
 */
public class Lists<T> {

    private Lists() {
    }

    @SafeVarargs
    public static <T> List<T> of(T... args) {
        return new ArrayList<>(Arrays.asList(args));
    }
}
