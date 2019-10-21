package io.jopen.distributelock.mysql;

import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 乐观锁（Optimistic Lock），顾名思义，就是很乐观，每次去拿数据的时候都认为别人不会修改，
 * 所以不会上锁，但是在提交更新的时候会判断一下在此期间别人有没有去更新这个数据。乐观锁适用
 * 于读多写少的应用场景，这样可以提高吞吐量。
 * <p>
 * 乐观锁：假设不会发生并发冲突，只在提交操作时检查是否违反数据完整性。 CAS机制
 * 修改之前先进行比较
 * SQL {update video set star = star +1 where `star` = star}
 *
 * @author maxuefeng
 * @since 2019/10/21
 */
public class MySQLOptimisticLockImpl {

    // 192.168.74.136:3306
    private final String uri = "";
    private Connection conn = null;

    @Before
    public void before() throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        conn = DriverManager.getConnection(uri, "root", "121101mxf@@ForBigData");
        conn.setAutoCommit(false);
    }

    @Test
    public void lock() throws SQLException, InterruptedException {
        String sql = "update video set star = 2 where star = 1";
        PreparedStatement ps = conn.prepareStatement(sql);
        int updateResult = ps.executeUpdate();

        // 如果小于0则一直循环 直到获取到锁
        while (updateResult <= 0) {
            updateResult = ps.executeUpdate();
        }
    }
}
