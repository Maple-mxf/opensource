package io.jopen.core.higherorderfunctions;

import com.google.common.base.Preconditions;
import org.junit.Test;

import java.util.stream.Stream;

/**
 * @author maxuefeng
 * @since 2019/10/22
 */
public class Java8Stream {

    @Test
    public void higherOrderFunctions(){

        //
        Stream<String> stream1 = Stream.of("Hello", "Spark", "Python")
                .map(s -> {
                    Preconditions.checkNotNull(s);
                    return s.toUpperCase();
                });


    }


}
