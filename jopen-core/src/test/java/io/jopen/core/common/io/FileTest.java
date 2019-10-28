package io.jopen.core.common.io;

import com.google.common.io.Files;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author maxuefeng
 * @since 2019/10/28
 */
public class FileTest {

    @Test
    public void getAbsolutePath() throws IOException {
        File file = new File("E:\\java-workplace\\opensource\\jopen-core\\src\\test\\java\\io\\jopen\\core\\common\\io\\");
        System.err.println(file.getAbsolutePath());

        String s = file.getAbsolutePath() + "/testDB";
        Files.touch(new File(s));
    }
}
