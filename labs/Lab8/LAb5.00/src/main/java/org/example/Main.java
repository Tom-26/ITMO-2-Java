package org.example;

import org.example.GUI.LoginFrame;
import org.example.Managers.DatabaseManager;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            Connection connection = DatabaseManager.getConnection();
            new LoginFrame(connection).setVisible(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
