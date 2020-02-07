package io.jopen.springboot.plugin.cache;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.junit.Test;

import java.nio.charset.Charset;

/**
 * @author maxuefeng
 * @see com.google.common.hash.BloomFilter
 * @see BloomFilter#mightContain(Object)
 * @see BloomFilter#put(Object)
 * @since 2020/2/7
 */
public class BloomFilterTest {

    @Test
    public void testBaseApi() {
        long expectedInsertions = 10000000;
        double fpp = 0.00001;

        BloomFilter<CharSequence> bloomFilter =
                BloomFilter.create(Funnels.stringFunnel(Charset.defaultCharset()), expectedInsertions, fpp);

        bloomFilter.put("1");

        boolean test = bloomFilter.test("2");
        boolean mightContain = bloomFilter.mightContain("2");
        System.err.println(test);
        System.err.println(mightContain);

    }
}
