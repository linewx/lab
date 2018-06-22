package com.zz.playground.database;

import ch.qos.logback.core.db.dialect.DBUtil;
import org.apache.commons.dbutils.DbUtils;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by luganlin on 22/06/2018.
 */
public class DatabaseTest {
    @Test
    public void transactionNonRepeatableTest() {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = Database.getConnection("postgresql", "localhost", "ht", "ht", "sandbox");
            stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery("select count(*) from test");
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("count"));
            }

        } catch(Exception e) {

        }finally {
            DbUtils.closeQuietly(stmt);
            DbUtils.closeQuietly(conn);
        }
        Assert.assertTrue(true);
    }

}
