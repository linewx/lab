package com.zz.playground.database;

import org.apache.commons.dbutils.DbUtils;
import org.junit.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by luganlin on 22/06/2018.
 */
//@RunWith(ParallelSuite.class)
//run in serial
public class DatabaseTransactionIsolationTest {
    private static Connection mngConnection;

//    @BeforeClass
//    public static void beforeClass() {
//        mngConnection = getNewConnection();
//
//    }
//
//    @AfterClass
//    public static void afterClass() {
//        //run once after any test method
//        DbUtils.closeQuietly(mngConnection);
//    }
//
//    @Before
//    public void setUp() throws Exception{
//        Statement clearStmt = mngConnection.createStatement();
//        clearStmt.execute("truncate table test");
//    }
//
//    @After
//    public void tearDown() throws Exception{
//        Statement clearStmt = mngConnection.createStatement();
//        clearStmt.execute("truncate table test");
//    }
//
//
//    public static Connection getNewConnection() {
//        return Database.getConnection("mysql", "localhost", "3306","ht", "ht", "sandbox");
//    }
//
//    //if dirty read or not
//    public static boolean isDirtyReadConnection(Connection conn) throws Exception{
//        Connection helpConn = null;
//        Statement stmt = null;
//        Statement helpStmt = null;
//        Statement mngStmt = null;
//        final String ORIGIN_NAME = "origin_name";
//        final String UPDATED_NAME = "updated_name";
//
//        String result = "";
//
//        try {
//            //prepare data
//            mngStmt = mngConnection.createStatement();
//            Integer testId = ThreadLocalRandom.current().nextInt(100, 10000);
//            mngStmt.execute(String.format("insert into test values(%d, '%s')", testId, ORIGIN_NAME));
//
//
//            //help session
//            helpConn = getNewConnection();
//            helpConn.setAutoCommit(false);
//            helpStmt = helpConn.createStatement();
//            helpStmt.execute(String.format("update test set name='%s' where id=%d", UPDATED_NAME, testId));
//
//            //get name during help session
//            stmt = conn.createStatement();
//            ResultSet rs = stmt.executeQuery(String.format("select name from test where id=%d", testId));
//
//            while(rs.next()) {
//                result = rs.getString("name");
//                break;
//            }
//
//            //rollback help session
//            helpConn.rollback();
//
//
//            //asset result
//            if (ORIGIN_NAME.equals(result)) {
//                return false;
//            }else if(UPDATED_NAME.equals(result)){
//                return true;
//            }else {
//                throw new Exception("it's impossible");
//            }
//        }
//        finally {
//            if (conn != null && !conn.getAutoCommit()) {
//                conn.rollback();
//            }
//            DbUtils.closeQuietly(mngStmt);
//            DbUtils.closeQuietly(stmt);
//            DbUtils.closeQuietly(helpStmt);
//            DbUtils.closeQuietly(helpConn);
//        }
//
//    }
//
//    public static boolean isNonRepeatableConnection(Connection conn) throws Exception{
//        Connection helpConn = null;
//        Statement stmt = null;
//        Statement helpStmt = null;
//        Statement mngStmt = null;
//        final String ORIGIN_NAME = "origin_name";
//        final String UPDATE_NAME = "updated_name";
//
//        try {
//
//            /*** process ****
//             * 0. create one row [randomId, 'origin_name']
//             * 1. session1, read the row once, return the origin_name
//             * 2. session2, update the row to [randomId, 'updated_name']
//             * 3. session1, read the row again, should return the origin_name to satisfy repeatable read needs
//             */
//
//            Integer testId = ThreadLocalRandom.current().nextInt(100, 10000);
//            //step 0
//            mngStmt = mngConnection.createStatement();
//            final String prepareDataSql = String.format("insert into test values(%d, '%s')", testId, ORIGIN_NAME);
//            mngStmt.execute(prepareDataSql);
//
//            //step 1
//            conn.setAutoCommit(false);
//            final String querySql = String.format("select name from test where id=%d", testId);
//            stmt = conn.createStatement();
//            ResultSet rs = stmt.executeQuery(querySql);
//            String firstQueryResult = "";
//            while(rs.next()) {
//                firstQueryResult = rs.getString("name");
//                break;
//            }
//
//            //step 2
//            helpConn = getNewConnection();
//            helpStmt = helpConn.createStatement();
//            final String updateSql = String.format("update test set name='%s' where id=%d", UPDATE_NAME, testId);
//            helpStmt.execute(updateSql);
//
//            //step 3
//            ResultSet rs2 = stmt.executeQuery(querySql);
//            String secondQueryResult = "";
//            while(rs2.next()) {
//                secondQueryResult = rs2.getString("name");
//                break;
//            }
//
//            conn.commit();
//
//            //todo: go deeper please
//            if (firstQueryResult !=null && !firstQueryResult.isEmpty()) {
//                if (firstQueryResult.equals(secondQueryResult)) {
//                    return false;
//                }else {
//                    return true;
//                }
//            }else {
//                throw new Exception("wrong result");
//            }
//        }
//        finally {
//            if (conn != null && !conn.getAutoCommit()) {
//                conn.rollback();
//            }
//            DbUtils.closeQuietly(mngStmt);
//            DbUtils.closeQuietly(stmt);
//            DbUtils.closeQuietly(helpStmt);
//            DbUtils.closeQuietly(helpConn);
//        }
//
//    }
//
//    public static boolean isPhantomReadConnection(Connection conn) throws Exception{
//
//        Connection helpConn = null;
//        Statement stmt = null;
//        Statement helpStmt = null;
//
//        /*** process ****
//         * 1. session1, get the total count for the first query
//         * 2. session2, insert a new record
//         * 3. session1, get the total count again, if on phantom read, the result should be the same
//         * difference with no-repeatable, phantom read impact the whole table, but non-repeatable only the existing row
//         */
//
//        try {
//            //target connection
//            int firstQueryCount = 0;
//            int secondQueryCount = 0;
//
//            //step1, get the total count for the first time
//            conn.setAutoCommit(false);
//            stmt = conn.createStatement();
//            final String queryCountSql = "select count(*) from test";
//
//            Integer testId = ThreadLocalRandom.current().nextInt(100, 10000);
//            String insertSql = String.format("insert into test values(%d, 'new_test')", testId);
//
//            ResultSet rs = stmt.executeQuery(queryCountSql);
//            while(rs.next()) {
//                firstQueryCount = rs.getInt(1);
//                break;
//            }
//
//            //step 2, insert a new record
//            helpConn = getNewConnection();
//            helpStmt = helpConn.createStatement();
//            helpStmt.execute(insertSql);
//
//            //step 3, query the total count again
//            rs = stmt.executeQuery("select count(*) from test");
//            while(rs.next()) {
//                secondQueryCount = rs.getInt(1);
//                break;
//            }
//
//            conn.commit();
//
//            if (firstQueryCount == secondQueryCount) {
//                return false;
//            }else {
//                return true;
//            }
//
//        }
//        finally {
//            if (conn != null && !conn.getAutoCommit()) {
//                conn.rollback();
//            }
//            DbUtils.closeQuietly(stmt);
//            DbUtils.closeQuietly(helpStmt);
//            DbUtils.closeQuietly(helpConn);
//        }
//
//    }
//
//    @Test
//    public void transactionLevelTest() throws Exception{
//        Connection conn = null;
//        try {
//            conn = getNewConnection();
//            conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
//            Assert.assertFalse(isDirtyReadConnection(conn));
//            Assert.assertTrue(isNonRepeatableConnection(conn));
//            Assert.assertTrue(isPhantomReadConnection(conn));
//
//            conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
//            Assert.assertFalse(isNonRepeatableConnection(conn));
//            //Assert.assertTrue(isPhantomReadConnection(conn));
//
//            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
//            Assert.assertFalse(isNonRepeatableConnection(conn));
//        }finally {
//            DbUtils.closeQuietly(conn);
//        }
//
//
//    }

}
