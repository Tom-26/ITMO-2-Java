package org.example.gui;

import org.example.DatabaseManager;
import org.example.models.User;
import org.example.services.AuthService;
import org.example.Commands.Realize.CommandExecutor;
import org.example.CollectionManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.security.NoSuchAlgorithmException;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private AuthService authService;
    private CommandExecutor commandExecutor;

    public LoginFrame(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
        setTitle("Авторизация");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("Логин:");
        usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Пароль:");
        passwordField = new JPasswordField();

        loginButton = new JButton("Войти");
        registerButton = new JButton("Регистрация");

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(registerButton);

        add(panel);

        try {
            Connection connection = DatabaseManager.getConnection();
            authService = new AuthService(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                try {
                    User user = authService.login(username, password);
                    if (user != null) {
                        commandExecutor.setCurrentUser(user);
                        MainFrame mainFrame = new MainFrame(commandExecutor.getCollectionManager().getRoutes());
                        mainFrame.setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(LoginFrame.this, "Неверный логин или пароль", "Ошибка авторизации", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException | NoSuchAlgorithmException ex) {
                    ex.printStackTrace();
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                try {
                    authService.register(username, password);
                    JOptionPane.showMessageDialog(LoginFrame.this, "Регистрация прошла успешно", "Регистрация", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException | NoSuchAlgorithmException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                Connection connection = DatabaseManager.getConnection();
                CollectionManager collectionManager = new CollectionManager(connection);
                CommandExecutor commandExecutor = new CommandExecutor(connection, collectionManager);
                LoginFrame frame = new LoginFrame(commandExecutor);
                frame.setVisible(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}
