package io.jopen.distributelock.mysql;

import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.util.concurrent.TimeUnit;

/**
 * 悲观锁（Pessimistic Lock），顾名思义，就是很悲观，每次去拿数据的时候都认为别人会修改，
 * 所以每次在拿数据的时候都会上锁，这样别人想拿这个数据就会block直到它拿到锁。
 * <p>
 * 悲观锁：假定会发生并发冲突，屏蔽一切可能违反数据完整性的操作。
 * <p>
 * SQL {select * from users where id =1 for update}
 *
 * @author maxuefeng
 * @since 2019/10/21
 */
public class MySQLPessimisticLockImpl {

    // 192.168.74.136:3306
    private final String uri = "jdbc:mysql://localhost:3306/mydb";
    private Connection conn = null;

    @Before
    public void before() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection(uri, "root", "121101mxf@@ForBigData");
    }

    @Test
    public boolean lock() throws SQLException, InterruptedException {
        conn.setAutoCommit(false);
        String sql;
        while (true) {
            try {
                // 悲观锁
                // 在查询语句后面增加for update，数据库会在查询过程中给数据库表增加排他锁
                sql = "select * from video where star=1 for update";

                // 执行查询
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet resultSet = ps.executeQuery();

                return resultSet.next();
            } catch (Exception ignored) {
            }
            // 线程休眠1秒
            TimeUnit.SECONDS.sleep(1);
        }
    }

    @Test
    public void unlock() throws SQLException {
        // 释放锁
        conn.commit();
    }
}
