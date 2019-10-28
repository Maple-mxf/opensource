package io.jopen.snack.common.listener;

import io.jopen.snack.common.event.SnackApplicationEvent;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * @author maxuefeng
 * @since 2019/10/28
 */
public abstract class DatabaseListener extends SnackApplicationListener {


    public static class Create extends DatabaseListener {
        @Override
        public void handEvent(@NonNull SnackApplicationEvent event) {

        }
    }
}
