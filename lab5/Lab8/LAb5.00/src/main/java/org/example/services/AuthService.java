package org.example.services;

import org.example.dao.UserDao;
import org.example.models.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;

public class AuthService {
    private final UserDao userDao;
    private final Connection connection;

    public AuthService(Connection connection) {
        this.userDao = new UserDao(connection);
        this.connection = connection;
    }

    public void register(String username, String password) throws SQLException, NoSuchAlgorithmException {
        String hashedPassword = hashPassword(password);
        userDao.registerUser(username, hashedPassword);
    }

    public User login(String username, String password) throws SQLException, NoSuchAlgorithmException {
        String hashedPassword = hashPassword(password);
        return userDao.getUser(username, hashedPassword);
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] hashBytes = md.digest(password.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public Connection getConnection() {
        return connection;
    }
}
