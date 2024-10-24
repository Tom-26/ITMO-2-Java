package org.example.GUI;

import org.example.Managers.LocalizationManager;
import org.example.services.AuthService;
import org.example.models.User;

import javax.swing.*;
import java.awt.*;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;

public class LoginFrame extends JFrame {
    private AuthService authService;
    private JLabel usernameLabel;
    private JTextField usernameField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private JComboBox<String> languageComboBox;

    public LoginFrame(Connection connection) {
        this.authService = new AuthService(connection);
        initializeUI();
    }

    private void initializeUI() {
        setTitle(LocalizationManager.getString("login"));
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        usernameLabel = new JLabel(LocalizationManager.getString("username"));
        usernameField = new JTextField(20);
        passwordLabel = new JLabel(LocalizationManager.getString("password"));
        passwordField = new JPasswordField(20);
        loginButton = new JButton(LocalizationManager.getString("login"));
        registerButton = new JButton(LocalizationManager.getString("sign_up"));
        languageComboBox = new JComboBox<>(new String[]{"English", "Русский", "Português", "Català", "Español (Panamá)"});

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            try {
                User user = authService.login(username, password);
                if (user != null) {
                    JOptionPane.showMessageDialog(this, LocalizationManager.getString("login_successful"));
                    new MainFrame(authService.getConnection(), user.getId()).setVisible(true);
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, LocalizationManager.getString("invalid_username_or_password"));
                }
            } catch (SQLException | NoSuchAlgorithmException ex) {
                JOptionPane.showMessageDialog(this, LocalizationManager.getString("login_error") + ": " + ex.getMessage());
            }
        });

        registerButton.addActionListener(e -> {
            new RegisterFrame(authService.getConnection()).setVisible(true);
            this.dispose();
        });

        languageComboBox.addActionListener(e -> {
            String selectedLanguage = (String) languageComboBox.getSelectedItem();
            switch (selectedLanguage) {
                case "English":
                    LocalizationManager.setLocale(new Locale("en", "US"));
                    break;
                case "Русский":
                    LocalizationManager.setLocale(new Locale("ru", "RU"));
                    break;
                case "Português":
                    LocalizationManager.setLocale(new Locale("pt", "PT"));
                    break;
                case "Català":
                    LocalizationManager.setLocale(new Locale("ca", "ES"));
                    break;
                case "Español (Panamá)":
                    LocalizationManager.setLocale(new Locale("es", "PA"));
                    break;
            }
            updateTexts();
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
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(loginButton, constraints);

        constraints.gridy = 3;
        panel.add(registerButton, constraints);

        constraints.gridy = 4;
        panel.add(languageComboBox, constraints);

        add(panel);
    }

    private void updateTexts() {
        setTitle(LocalizationManager.getString("login"));
        usernameLabel.setText(LocalizationManager.getString("username"));
        passwordLabel.setText(LocalizationManager.getString("password"));
        loginButton.setText(LocalizationManager.getString("login"));
        registerButton.setText(LocalizationManager.getString("sign_up"));
    }
}
