package io.jopen.core.common.io;

import org.junit.Test;

import java.io.IOException;

/**
 * @author maxuefeng
 * @since 2019/8/12
 */
public class ImageHelperTest {

    // Test Pass
    @Test
    public void testImage2String() throws IOException {
        String based64 = ImageHelper.image2StringEncodeBased64("E:\\workplace\\jopen-core\\tmp\\1.jpg");
        System.err.println(based64);

        ImageHelper.string2Image(based64);
    }
}
