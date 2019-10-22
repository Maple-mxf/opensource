package io.jopen.db.hive;

import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author maxuefeng
 * @since 2019/10/22
 * <p>{@link }</p>
 */
public class HiveJavaClient {


    @Before
    public void before() throws ClassNotFoundException {
        Class.forName("org.apache.hive.jdbc.HiveDriver");
    }

    @Test
    public void connection() throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:hive2://hadoop.madmant.com:10000/default", "hadoop", "aaa111");
        Statement stmt = con.createStatement();
        String sql = "select deptno,dname,BLOCK__OFFSET__INSIDE__FILE from dept";
    }


}
