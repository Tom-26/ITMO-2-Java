package org.example.Commands.Realize;

import org.example.Commands.BaseInterfaceCommand;
import org.example.ConsoleBuisness.CommandExecutorInterface;
import org.example.services.AuthService;
import org.example.models.User;
import org.example.Exception.InvalidUsernameException;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import java.io.Console;

public class RegisterCommand implements BaseInterfaceCommand {
    private final AuthService authService;
    private final CommandExecutorInterface commandExecutor;

    public RegisterCommand(Connection connection, CommandExecutorInterface commandExecutor) {
        this.authService = new AuthService(connection);
        this.commandExecutor = commandExecutor;
    }

    @Override
    public void execute(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            String username;
            while (true) {
                System.out.print("Введите имя пользователя (без пробелов): ");
                username = scanner.nextLine().trim();
                try {
                    if (username.contains(" ")) {
                        throw new InvalidUsernameException("Имя пользователя не должно содержать пробелы.");
                    }
                    break;
                } catch (InvalidUsernameException e) {
                    System.out.println(e.getMessage());
                }
            }

            String password;
            String confirmPassword;
            Console console = System.console();
            do {
                if (console == null) {
                    // Fall back to Scanner if console is not available (for example, in some IDEs)
                    System.out.print("Введите пароль: ");
                    password = scanner.nextLine().trim();
                    System.out.print("Подтвердите пароль: ");
                    confirmPassword = scanner.nextLine().trim();
                } else {
                    password = new String(console.readPassword("Введите пароль: "));
                    confirmPassword = new String(console.readPassword("Подтвердите пароль: "));
                }

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
