package org.example.dao;

import org.example.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    private Connection connection;

    public UserDao(Connection connection) {
        this.connection = connection;
    }

    public void registerUser(String username, String passwordHash) throws SQLException {
        String sql = "INSERT INTO users (username, password_hash) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, passwordHash);
            statement.executeUpdate();
            System.out.println("User " + username + " registered successfully.");
        } catch (SQLException e) {
            System.err.println("Error registering user " + username + ": " + e.getMessage());
            throw e;
        }
    }

    public User getUser(String username, String passwordHash) throws SQLException {
        String sql = "SELECT id, username FROM users WHERE username = ? AND password_hash = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, passwordHash);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new User(resultSet.getInt("id"), resultSet.getString("username"));
            } else {
                System.out.println("User " + username + " not found or password incorrect.");
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Error fetching user " + username + ": " + e.getMessage());
            throw e;
        }
    }
}
