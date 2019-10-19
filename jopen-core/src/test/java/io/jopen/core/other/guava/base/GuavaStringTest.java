package io.jopen.core.other.guava.base;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * @author maxuefeng
 * @since 2019/10/19
 */
public class GuavaStringTest {

    @Test
    public void testCollectionEle2String(){
        ImmutableList<String> list = ImmutableList.of("one", "two");
        String result = Joiner.on("-").join(list);
        // result:one-two
        System.err.println(result);
    }

    @Test
    public void testMapKeyAndValueJoin2String(){
        ImmutableMap<String, String> map = ImmutableMap.of("k1", "v1", "k2", "v2");
        String result = Joiner.on(",").withKeyValueSeparator("=").join(map);
        // result:k1=v1,k2=v2
        System.err.println(result);
    }

    @Test
    public void transformString2Collection(){
        String src = "1-2-3";
        List<String> list = Splitter.on("-").splitToList(src);
        // result:[1, 2, 3]
        System.err.println(list);
    }

    @Test
    public void transformString2Map(){
        String src = "k1=v1,k2=v2";
        Map<String, String> map = Splitter.on(",").withKeyValueSeparator("=").split(src);
        // result:{k1=v1, k2=v2}
        System.err.println(map);
    }
}
