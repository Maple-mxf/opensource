package io.jopen.core.other.guava.base;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.ByteSource;
import com.google.common.io.Files;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

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
    }
}
