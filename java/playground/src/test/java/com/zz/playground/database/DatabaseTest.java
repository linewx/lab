package com.zz.playground.database;

import ch.qos.logback.core.db.dialect.DBUtil;
import com.googlecode.junittoolbox.ParallelSuite;
import org.apache.commons.dbutils.DbUtils;
import org.junit.*;
import org.junit.runner.RunWith;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by luganlin on 22/06/2018.
 */
//@RunWith(ParallelSuite.class)
//run in serial
public class DatabaseTest {
    public Connection mngConnection;
    public Connection conn1;
    public Connection conn2;

    @BeforeClass
    public void beforeClass() {
        mngConnection = Database.getConnection("postgresql", "localhost", "5434","ht", "ht", "sandbox");
        //run once before any test method

    }

    @AfterClass
    public void afterClass() {
        //run once after any test method
        DbUtils.closeQuietly(mngConnection);
    }

    @Before
    public void setUp() throws Exception{
        Statement clearStmt = mngConnection.createStatement();
        clearStmt.execute("truncate table test");
    }

    @After
    public void tearDown() throws Exception{
        Statement clearStmt = mngConnection.createStatement();
        clearStmt.execute("truncate table test");
    }

    @Test
    public void test1() {
        System.out.println("test1");
    }

    @Test
    public void test2() {
        System.out.println("test2");
    }

    @Test
    public void test3() {
        System.out.println("test3");
    }

    public static Connection getNewConnection() {
        return Database.getConnection("postgresql", "localhost", "5434","ht", "ht", "sandbox");
    }

    public boolean isRepeatableConnection(Connection conn) {
        Connection testConn = null;
        try {
            testConn = getNewConnection();
            testConn.setAutoCommit(false);
        }catch (Exception e) {
            
        }

    }

    @Test
    public void transactionNonRepeatableTest() throws Exception{
//        Connection conn1 = null;
//        Connection conn2 = null;
//        Connection conn = null;
//        Statement stmt1 = null;
//        Statement stmt2 = null;
//        Statement clearStatement = null;
//        try {
//            conn = Database.getConnection("postgresql", "localhost", "5434","ht", "ht", "sandbox");
//            //clear database
//            clearStatement = conn.createStatement();
//            clearStatement.execute("truncate table test");
//
//
//            //session 1
//            conn1 = Database.getConnection("postgresql", "localhost", "5434","ht", "ht", "sandbox");
//            conn1.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
//            conn1.setAutoCommit(false);
//            stmt1 = conn1.createStatement();
//            ResultSet rs1 = stmt1.executeQuery("select count(*) from test");
//            while (rs1.next()) {
//                Assert.assertEquals(rs1.getInt("count"), 0);
//                break;
//            }
//
//            //session 2
//            conn2 = Database.getConnection("postgresql", "localhost", "5434","ht", "ht", "sandbox");
//            stmt2 = conn2.createStatement();
//            Boolean rs2 = stmt2.execute("insert into test values(1, 'test')");
//            Assert.assertEquals(rs2, false);
//
//
//            //back to session1 again
//            rs1 = stmt1.executeQuery("select count(*) from test");
//            while (rs1.next()) {
//                Assert.assertEquals(rs1.getInt("count"), 0);
//                break;
//            }
//
//            conn1.commit();
//
//            //clear database again
//            clearStatement.execute("truncate table test");
//
//        } finally {
//
//            conn1.rollback();
//            DbUtils.closeQuietly(stmt1);
//            DbUtils.closeQuietly(stmt2);
//            DbUtils.closeQuietly(clearStatement);
//            DbUtils.closeQuietly(conn);
//
//
//        }
    }

}
