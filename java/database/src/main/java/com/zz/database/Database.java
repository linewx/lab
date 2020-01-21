
package com.zz.database;

import org.apache.commons.dbutils.DbUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lugan on 5/17/2016.
 */
public class Database {
    public static void main(String... argv) throws Exception {
//        System.out.println(getTables("postgresql", "localhost", "ht", "ht", "shengwu_report"));
//        System.out.println(getColumns("postgresql", "localhost", "ht", "ht", "shengwu_report", "datasource_connection"));

        boolean result = Database.testConnection("jdbc:mysql://localhost:3306/datalogic_new?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true", "root", "root");
        System.out.println("connection succssfully");
        Connection conn = null;
        try {
            conn = Database.getConnection("mysql", "localhost", "root", "1Qaz2wsx", "acm");
            Database.execStatement(conn, "select * from table1 join table2 on table1.shared = table2.shared join table3 on table2.shared=table3.shared");
        }finally {
            DbUtils.closeQuietly(conn);
        }



    }

    private static String getConnectionString(String type, String host, String database) {
        return String.format("jdbc:%s://%s/%s", type, host, database);
    }

    public static boolean testConnection(String jdbcConnectionString, String userName, String password) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(jdbcConnectionString, userName, password);
            return true;
        } catch (SQLException e) {
            return false;
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    public static Connection getConnection(String jdbcConnectionString, String userName, String password) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(jdbcConnectionString, userName, password);
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    public static boolean testConnection(String type, String host, String userName, String password, String database) {
        Connection conn = null;
        try {
            String jdbcConnectionString = String.format("jdbc:%s://%s/%s", type, host, database);

            conn = DriverManager.getConnection(jdbcConnectionString, userName, password);
            return true;
        } catch (SQLException e) {
            return false;
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    public static List<String> getTables(String type, String host, String userName, String password, String database) throws SQLException {
        List<String> tables = new ArrayList<>();
        Connection conn = null;
        ResultSet rs = null;


        try {
            conn = DriverManager.getConnection(Database.getConnectionString(type, host, database), userName, password);
            DatabaseMetaData md = conn.getMetaData();
            //todo: refine to use the function params
            rs = md.getTables(null, null, "%", null);
            while (rs.next()) {
                String tableSchema = rs.getString("table_schem");
                String tableType = rs.getString("table_type");
                String tableName = rs.getString("table_name");

                if ("public".equals(tableSchema) && "TABLE".equals(tableType)) {
                    //System.out.println("table name:" + ":" + tableName);
                    tables.add(tableName);
                }
            }
            return tables;
        } finally {
            DbUtils.closeQuietly(conn);
            DbUtils.closeQuietly(rs);
        }
    }

    public static List<String> getColumns(String type, String host, String userName, String password, String database, String tableName) throws SQLException {
        List<String> columns = new ArrayList<>();
        Connection conn = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(Database.getConnectionString(type, host, database), userName, password);
            DatabaseMetaData md = conn.getMetaData();
            rs = md.getColumns(null, null, tableName, null);
            ResultSetMetaData rsma = rs.getMetaData();
            for (int i = 0; i < rsma.getColumnCount(); i++) {
                System.out.println(rsma.getColumnName(i + 1));
            }

            while (rs.next()) {
                String columnName = rs.getString("COLUMN_NAME");
                columns.add(columnName);
            }
            return columns;
        } finally {
            DbUtils.closeQuietly(conn);
            DbUtils.closeQuietly(rs);
        }
    }

    public static Connection getConnection(String type, String host, String userName, String password, String database) {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //conn = DriverManager.getConnection(Database.getConnectionString(type, host, database), userName, password);
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/passport?useUnicode=true&characterEncoding=utf-8&mysqlEncoding=utf8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&useServerPrepStmts=true&cachePrepStmts=true&prepStmtCacheSize=250&prepStmtCacheSqlLimit=2048", "root", "sds");
            return conn;
        } catch (Exception e) {
            DbUtils.closeQuietly(conn);
        }

        return null;
    }

    public static void testQueryString() {
    }

    public static void execStatement(Connection conn, String query) {


        Statement state = null;
        try {
            if (conn == null) {
                return;
            } else {
                state = conn.createStatement();
                ResultSet rs = state.executeQuery(query);
                ResultSetMetaData rsma = rs.getMetaData();
                while(rs.next()) {
                    String columnValue = rs.getString("column1");
                    System.out.println(columnValue);
                }
            }

        } catch (Exception e) {

        } finally {
            DbUtils.closeQuietly(conn);
            DbUtils.closeQuietly(state);
        }

    }


}
