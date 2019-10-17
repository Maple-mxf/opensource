package io.jopen.core.function.predicate;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author maxuefeng
 * @since 2019-04-28
 */
public class BooleanPredicate {

    // private <T> T value;

    private Boolean flag = false;

    private BooleanPredicate(Boolean flag) {
        this.flag = flag;
    }

    public static BooleanPredicate andNoNUll(Predicate<Object> predicate, Object t) {
        return new BooleanPredicate(predicate.test(t));
    }

    public static BooleanPredicate andNoNUll(Object t) {
        return andNoNUll(Objects::nonNull, t);
    }

    public static BooleanPredicate and(boolean... args) {

        for (boolean arg : args) {
            if (!arg)
                return new BooleanPredicate(false);
        }

        return new BooleanPredicate(true);
    }

    public static Boolean or(boolean... args) {

        for (boolean arg : args) {
            if (arg)
                return true;
        }
        return false;
    }

    public Boolean get() {
        return this.flag;
    }

    public <T> T after(Supplier<T> supplier, Supplier<T> elseSupplier) {
        if (flag) {
            return supplier.get();
        } else {
            return elseSupplier.get();
        }
    }

    // @Deprecated
    public <T> void after(Consumer<T> t, T u) {
        if (flag) {
            t.accept(u);
        }
    }
}
