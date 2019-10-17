package io.jopen.core.common.util;

import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;

/**
 * @author maxuefeng
 */
public class NumberUtilTest {

    @Test
    public void testIsDigits(){
        String s = "-10.0012";

       // NumberUtils.isParsable()
        boolean digits = NumberUtils.isParsable(s);

        System.err.println(digits);
    }
}
