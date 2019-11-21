package io.jopen.core.common.sign;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * @author maxuefeng
 * @since 2019/11/21
 */
public class Base64Test {

    @Test
    public void simpleTest() throws UnsupportedEncodingException {
        byte[] ret = java.util.Base64.getDecoder().decode("eyJpZCI6ICIwMDAwMDEiLCAibWF0Y2hfaXRlbSI6IFtdLCAibWF0Y2hfZGF0ZSI6IFtdLCAibWF0Y2hfcHJpY2UiOiBbXSwgIm1hdGNoX2NvbW1vZGl0aWVzIjogW119");

        System.err.println(new String(ret, StandardCharsets.UTF_8));
    }
}
