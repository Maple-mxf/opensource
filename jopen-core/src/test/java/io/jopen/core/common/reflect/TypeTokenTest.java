package io.jopen.core.common.reflect;

import com.google.common.reflect.TypeToken;
import org.junit.Test;
/**
 * {@link com.google.common.reflect.TypeToken}
 *
 * @author maxuefeng
 * @since 2019/11/16
 */

public class TypeTokenTest<T> {

    @Test
    public void simpleTest() {
        TypeToken<TypeTokenTest<String>> typeToken = new TypeToken<TypeTokenTest<String>>() {
        };
        System.err.println(typeToken.resolveType(TypeTokenTest.class.getTypeParameters()[0]));
    }


}
