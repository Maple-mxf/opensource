package io.jopen.core.common.util;

import io.jopen.core.common.io.FileHelper;
import io.jopen.core.common.time.Formatter;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * @author maxuefeng
 */
public class FileHelperTest {

    @Test
    public void testGetFileSize() {
        File file = new File("E:\\workplace\\biz-api\\tmp\\1.jpg");

        // System.err.println(FileHelper.getFileSizeBytes(file));

        System.err.println(FileHelper.getFileSizeKiloBytes(file));

       // System.err.println(FileHelper.getFileSizeMegaBytes(file));
    }

    /**
     * 80634765625KB
     *
     * @throws IOException
     */
    @Test
    public void testGetFileSizeByteArray() throws IOException {
        File file = new File("E:\\workplace\\biz-api\\tmp\\1.jpg");

        byte[] src = com.google.common.io.Files.toByteArray(file);

        double size = FileHelper.getFileSizeKiloBytes(src);

        System.err.println(size);
    }

    @Test
    public void testGetFileCreateDate(){
        Date createTime = FileHelper.getFileCreateTime("C:\\Users\\EDZ\\Desktop\\新建文本文档.txt");

        System.err.println(DateFormatUtils.format(createTime, Formatter.P.P4) );
    }
}
