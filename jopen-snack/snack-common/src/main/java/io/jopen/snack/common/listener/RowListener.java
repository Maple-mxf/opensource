package io.jopen.snack.common.listener;

import io.jopen.snack.common.event.SnackApplicationEvent;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * @author maxuefeng
 * @since 2019/10/28
 */
public abstract class RowListener extends SnackApplicationListener {

    public static class Insert extends RowListener{
        @Override
        public void apply(@NonNull SnackApplicationEvent event) {

        }
    }

    public static class Delete extends RowListener{
        @Override
        public void apply(@NonNull SnackApplicationEvent event) {

        }
    }

    public static class Update extends RowListener{
        @Override
        public void apply(@NonNull SnackApplicationEvent event) {

        }
    }

    public static class Query extends RowListener{
        @Override
        public void apply(@NonNull SnackApplicationEvent event) {

        }
    }
}
