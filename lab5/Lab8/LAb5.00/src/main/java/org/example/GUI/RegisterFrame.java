package org.example.GUI;

import org.example.Managers.LocalizationManager;
import org.example.services.AuthService;
import org.example.models.User;

import javax.swing.*;
import java.awt.*;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;

public class RegisterFrame extends JFrame {
    private AuthService authService;

    public RegisterFrame(Connection connection) {
        this.authService = new AuthService(connection);
        initializeUI();
    }

    private void initializeUI() {
        setTitle(LocalizationManager.getString("register"));
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        JLabel usernameLabel = new JLabel(LocalizationManager.getString("username"));
        JTextField usernameField = new JTextField(20);
        JLabel passwordLabel = new JLabel(LocalizationManager.getString("password"));
        JPasswordField passwordField = new JPasswordField(20);
        JLabel confirmPasswordLabel = new JLabel(LocalizationManager.getString("confirm_password"));
        JPasswordField confirmPasswordField = new JPasswordField(20);
        JButton registerButton = new JButton(LocalizationManager.getString("continue"));
        JButton loginButton = new JButton(LocalizationManager.getString("back_to_login"));

        registerButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, LocalizationManager.getString("passwords_do_not_match"));
                return;
            }

            try {
                authService.register(username, password);
                JOptionPane.showMessageDialog(this, LocalizationManager.getString("registration_successful"));
                User user = authService.login(username, password);
                if (user != null) {
                    new MainFrame(authService.getConnection(), user.getId()).setVisible(true);
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, LocalizationManager.getString("automatic_login_error"));
                }
            } catch (SQLException | NoSuchAlgorithmException ex) {
                JOptionPane.showMessageDialog(this, LocalizationManager.getString("registration_error") + ": " + ex.getMessage());
            }
        });

        loginButton.addActionListener(e -> {
            new LoginFrame(authService.getConnection()).setVisible(true);
            this.dispose();
        });

        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(usernameLabel, constraints);

        constraints.gridx = 1;
        panel.add(usernameField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(passwordLabel, constraints);

        constraints.gridx = 1;
        panel.add(passwordField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(confirmPasswordLabel, constraints);

        constraints.gridx = 1;
        panel.add(confirmPasswordField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(registerButton, constraints);

        constraints.gridy = 4;
        panel.add(loginButton, constraints);

        add(panel);
    }
}
