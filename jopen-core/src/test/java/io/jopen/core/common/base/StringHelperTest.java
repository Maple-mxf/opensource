package io.jopen.core.common.base;

import io.jopen.core.common.text.StringHelper;
import org.junit.Test;

/**
 * @author maxuefeng
 * @since 2019/10/12
 */
public class StringHelperTest {

    @Test
    public void testGetRandomString(){
        for (int i = 0; i < 1000; i++) {
            System.err.println(StringHelper.randomString(10));
        }
    }
}
