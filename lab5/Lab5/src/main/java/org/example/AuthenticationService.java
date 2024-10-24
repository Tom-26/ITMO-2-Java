package org.example;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public class AuthenticationService {

    private UserDAO userDAO;

    public AuthenticationService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    // Метод для регистрации пользователя
    public boolean register(String username, String password) {
        if (userDAO.userExists(username)) {
            return false; // Пользователь с таким именем уже существует
        }

        String passwordHash = hashPassword(password);
        User user = new User(username, passwordHash);
        return userDAO.addUser(user);
    }

    // Метод для авторизации пользователя
    public User login(String username, String password) {
        User user = userDAO.getUserByUsername(username);
        if (user != null && user.getPasswordHash().equals(hashPassword(password))) {
            return user;
        }
        return null;
    }

    // Метод для хэширования пароля с использованием SHA-1
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] hashBytes = md.digest(password.getBytes());
            return byteArrayToHex(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-1 algorithm not found", e);
        }
    }

    // Метод для преобразования байтового массива в шестнадцатеричную строку
    private String byteArrayToHex(byte[] bytes) {
        Formatter formatter = new Formatter();
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }
}
