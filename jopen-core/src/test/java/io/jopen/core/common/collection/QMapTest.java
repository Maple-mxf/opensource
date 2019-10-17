package io.jopen.core.common.collection;

import org.junit.Test;

import java.util.Map;

/**
 * @author maxuefeng
 */
public class QMapTest {

    @Test
    public void simpleTestMap(){
        Map map = QMap.of(1, 2, 3, 4, 5, "7", 7, 8,0);
        System.err.println(map);
    }
}
