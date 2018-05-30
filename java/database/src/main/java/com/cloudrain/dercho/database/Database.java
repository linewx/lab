package com.cloudrain.dercho.database;

import java.sql.*;
import java.util.Date;

/**
 * Created by lugan on 5/17/2016.
 */
public class Database {
    public static void main(String... argv) {

        System.out.println("-------- PostgreSQL "
                + "JDBC Connection Testing ------------");

        try {

            Class.forName("org.postgresql.Driver");

        } catch (ClassNotFoundException e) {

            System.out.println("Where is your PostgreSQL JDBC Driver? "
                    + "Include in your library path!");
            e.printStackTrace();
            return;

        }

        System.out.println("PostgreSQL JDBC Driver Registered!");

        Connection conn = null;

        try {

            conn = DriverManager.getConnection(
                    "jdbc:postgresql://127.0.0.1:5434/test", "postgres",
                    "admin");

            // 开时时间
            Long begin = new Date().getTime();
            // sql前缀
            String SQL = "INSERT INTO Employees (id, first, last, age) " +
                    "VALUES(?, ?, ?, ?)";
            try {

                conn.setAutoCommit(false);
                PreparedStatement pstmt = conn.prepareStatement(SQL);
                for (int i = 1; i <= 100; i++) {
                    for (int j = 1; j <= 10000; j++) {
                        pstmt.setInt( 1, 400 );
                        pstmt.setString( 2, "Pappu" );
                        pstmt.setString( 3, "Singh" );
                        pstmt.setInt( 4, 33 );
                        pstmt.addBatch();
                    }

                    pstmt.executeBatch();
                    conn.commit();
                }
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            // 结束时间
            Long end = new Date().getTime();
            // 耗时
            System.out.println("cast : " + (end - begin) / 1000 + " ms");

        } catch (SQLException e) {

            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;

        }

        if (conn != null) {
            System.out.println("You made it, take control your database now!");
        } else {
            System.out.println("Failed to make connection!");
        }

    }
}
