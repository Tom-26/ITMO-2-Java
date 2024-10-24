package org.example.Commands.Realize;

import org.example.Commands.BaseInterfaceCommand;
import org.example.models.User;
import org.example.services.AuthService;
import org.example.ConsoleCommandExecutor;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class RegisterCommand implements BaseInterfaceCommand {
    private final AuthService authService;
    private final ConsoleCommandExecutor commandExecutor;

    public RegisterCommand(Connection connection, ConsoleCommandExecutor commandExecutor) {
        this.authService = new AuthService(connection);
        this.commandExecutor = commandExecutor;
    }

    @Override
    public void execute(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Введите имя пользователя: ");
            String username = scanner.nextLine().trim();

            String password;
            String confirmPassword;
            do {
                System.out.print("Введите пароль: ");
                password = scanner.nextLine().trim();
                System.out.print("Подтвердите пароль: ");
                confirmPassword = scanner.nextLine().trim();

                if (!password.equals(confirmPassword)) {
                    System.out.println("Пароли не совпадают. Попробуйте еще раз.");
                }
            } while (!password.equals(confirmPassword));

            authService.register(username, password);
            System.out.println("Пользователь успешно зарегистрирован.");

            // Автоматически логиним пользователя после успешной регистрации
            User user = authService.login(username, password);
            if (user != null) {
                commandExecutor.setCurrentUser(user);
                System.out.println("Вход выполнен автоматически после регистрации.");
            } else {
                System.out.println("Ошибка автоматического входа после регистрации.");
            }
        } catch (SQLException | NoSuchAlgorithmException e) {
            System.err.println("Ошибка при регистрации пользователя: " + e.getMessage());
        }
    }

    @Override
    public String getName() {
        return "register";
    }
}
