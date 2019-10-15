package core.common.model;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.util.List;

/**
 * @author maxuefeng
 * @since 2019/8/15
 */
public class GuavaTable {

    public static <R, C, V> Table<R, C, V> put(GuavaTableEle<R, C, V>... els) {

        Table<R, C, V> table = HashBasedTable.create();

        for (GuavaTableEle<R, C, V> el : els) {
            table.put(el.rowKey, el.columnKey, el.value);
        }

        return table;
    }

    public static Table<String, String, String> put(List<HTableCell> cells) {

        Table<String, String, String> table = HashBasedTable.create();

        for (HTableCell cell : cells) {
            String[] fq = cell.getFamilyAndQualifier().split("-");
            table.put(fq[0], fq[1], cell.getVal());
        }

        return table;
    }
}
