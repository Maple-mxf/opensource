package io.jopen.distributelock.mysql;

import org.junit.Before;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
    private final String uri = "";
    private Connection conn = null;

    @Before
    public void before() throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        conn = DriverManager.getConnection(uri, "root", "121101mxf@@ForBigData");
        conn.setAutoCommit(false);
    }
}
