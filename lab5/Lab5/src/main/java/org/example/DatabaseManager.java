package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    // Константы для подключения к базе данных
    private static final String URL = "jdbc:postgresql://db:5432/studs"; // Замените на правильный адрес
    private static final String USER = "s413081"; // Имя пользователя
    private static final String PASSWORD = "FpA3ON60fR9G50fd"; // Пароль

    // Метод для получения соединения с базой данных
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Метод для закрытия соединения
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
