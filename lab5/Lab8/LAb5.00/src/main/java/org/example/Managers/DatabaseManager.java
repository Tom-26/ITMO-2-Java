package org.example.Managers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String URL = "jdbc:postgresql://localhost:5433/postgres";
    private static final String USER = "maksim";
    private static final String PASSWORD = "luvisrage";

    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Соединение установлено успешно.");
        } catch (SQLException e) {
            System.err.println("Не удалось установить соединение: " + e.getMessage());
            throw e;
        }
        return connection;
    }
}
