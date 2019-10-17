package io.jopen.core.common.collection;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Table;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author maxuefeng
 * @see Table
 */
public class TableTest {

    @Test
    public void testSimpleAPI() {

        Table<Object, Object, Object> table = HashBasedTable.create();

        table.put("ID", "C1", "V1");
        table.put("ID", "C2", "V2");
        table.put("ID", "C3", "V3");
        table.put("ID", "C4", "V4");

        System.err.println(table);
    }
}
