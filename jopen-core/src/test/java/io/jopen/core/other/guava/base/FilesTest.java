package io.jopen.core.other.guava.base;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.ByteSource;
import com.google.common.io.Files;
import com.google.common.io.Resources;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author maxuefeng
 * @since 2019/10/19
 */
public class FilesTest {

    @Test
    public void testGetHash() throws IOException {
        // HashCode hashCode = Files.asByteSource(new File()).hash();
        // HashCode hash = ByteSource.wrap().hash(Hashing.sha256());
        // Throwables.getCausalChain()
        ByteSource.empty().hash(Hashing.goodFastHash(1));

        HashCode hashCode = Files.asByteSource(new File("E:\\java-workplace\\opensource\\jopen-core\\src\\test\\java\\io\\jopen\\core\\other\\guava\\GuavaSetTest.java"))
                .hash(Hashing.goodFastHash(1));

        String str = hashCode.toString();
        System.err.println(str);

        // Files.map().force().asIntBuffer()

        // Files.asCharSink().writeFrom()
        // Files.isDirectory().apply()

        // 把文件读取到list集合中
        List<String> list = Files.readLines(new File(".\\1.txt"), StandardCharsets.UTF_8);

        // 把文件转换为byte数组
        byte[] fileBytes = Files.map(new File(".\\1.txt")).array();

        // Files.asCharSink(new File(".\\1.txt"),StandardCharsets.UTF_8, FileWriteMode.APPEND).openStream().write();

        // 拷贝文件
        Files.copy(new File(".\\1.txt"),new File(".\\2.txt"));

        // 获取classpath下的xml
        URL url = Resources.getResource("application.xml");

        // Files.asByteSource(new File(".\\1.txt")).size()
    }
}
