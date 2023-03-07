package org.example.repositories;

import java.sql.*;

public class ConnectionFactory {
    String connectionUrl = "jdbc:mysql://localhost:3306/dbName";
    String dbUser = "dbUser";
    String dbPwd = "dbPassword";

    private static ConnectionFactory connectionFactory = null;

    private ConnectionFactory() { }

    public Connection getConnection() throws SQLException {
        Connection conn = null;
        conn = DriverManager.getConnection(connectionUrl, dbUser, dbPwd);
        return conn;
    }

    public static ConnectionFactory getInstance() {
        if (connectionFactory == null) {
            connectionFactory = new ConnectionFactory();
        }
        return connectionFactory;
    }
}
