package org.example.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MCPConnection {

    private static final String URL = "jdbc:sqlite:C:\\Users\\dev\\IdeaProjects\\FinaliProject_1\\FinalProject5.db";
    public static Connection getConn() throws ClassNotFoundException, SQLException {

        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(URL);
        return conn;
    }
}
