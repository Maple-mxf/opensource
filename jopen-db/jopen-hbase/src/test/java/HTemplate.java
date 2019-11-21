import io.jopen.hbase.annotation.RowKey;
import io.jopen.hbase.annotation.Table;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.LongComparator;
import org.apache.hadoop.hbase.filter.ValueFilter;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Date;

/**
 * @author maxuefeng
 * @since 2019/11/9
 */
public class HTemplate {

    private Admin admin;
    private Connection connection;


    @Before
    public void before() throws IOException {
        Configuration conf = HBaseConfiguration.create();

        conf.set("hbase.zookeeper.quorum", "192.168.1.84");
        connection = ConnectionFactory.createConnection(conf);
        this.admin = connection.getAdmin();
    }

    public void createTable() throws IOException {
        TableDescriptor tableDescriptor = TableDescriptorBuilder.newBuilder(TableName.valueOf("user"))
                .setColumnFamily(new ColumnFamilyDescriptorBuilder.ModifyableColumnFamilyDescriptor("personal".getBytes()))
                .setColumnFamily(new ColumnFamilyDescriptorBuilder.ModifyableColumnFamilyDescriptor("other".getBytes()))
                .build();

        admin.createTable(tableDescriptor);
    }

    public <T> void save(@NonNull T t) throws IOException {
        Table ta = t.getClass().getDeclaredAnnotation(Table.class);
        org.apache.hadoop.hbase.client.Table table = connection.getTable(TableName.valueOf(ta.value()));
        RowKey ra = t.getClass().getDeclaredAnnotation(RowKey.class);
        Put put = new Put(ra.value().getBytes());

        // put.addColumn()
        Field[] fields = t.getClass().getDeclaredFields();

        // Stream.of(fields).peek(field -> field.setAccessible(true))
        for (Field field : fields) {
            field.setAccessible(true);
//            field
        }

        //
//        table.put();
    }

    /**
     * @throws IOException
     * @see CompareFilter
     */
    public void select() throws IOException {

        org.apache.hadoop.hbase.client.Table table = connection.getTable(TableName.valueOf(""));
        Filter filter = new ValueFilter(CompareOperator.LESS, new LongComparator(new Date().getTime()));
        Scan scan = new Scan();
        scan.setFilter(filter);

        ResultScanner results = table.getScanner(scan);

        for (Result result : results) {
            System.err.println(result);
        }
    }

    @Test
    public void testCreateTable() throws IOException {
        this.createTable();
    }
}
