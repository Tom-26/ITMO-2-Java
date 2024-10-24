package org.example.Commands.Realize;

import org.example.Commands.BaseInterfaceCommand;
import org.example.ConsoleBuisness.CommandExecutorInterface;
import org.example.services.AuthService;
import org.example.models.User;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.io.Console;
import java.util.Scanner;

public class LoginCommand implements BaseInterfaceCommand {
    private final AuthService authService;
    private final CommandExecutorInterface commandExecutor;

    public LoginCommand(Connection connection, CommandExecutorInterface commandExecutor) {
        this.authService = new AuthService(connection);
        this.commandExecutor = commandExecutor;
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 1) {
            System.out.println("Недостаточно аргументов для входа. Введите имя пользователя.");
            return;
        }

        String username = args[0];
        String password;

        Console console = System.console();
        if (console == null) {
            // Fall back to Scanner if console is not available (for example, in some IDEs)
            Scanner scanner = new Scanner(System.in);
            System.out.print("Введите пароль: ");
            password = scanner.nextLine().trim();
        } else {
            password = new String(console.readPassword("Введите пароль: "));
        }

        try {
            User user = authService.login(username, password);
            if (user != null) {
                System.out.println("Вход успешно выполнен.");
                commandExecutor.setCurrentUser(user);
            } else {
                System.out.println("Ошибка авторизации. Неверный логин или пароль.");
            }
        } catch (SQLException | NoSuchAlgorithmException e) {
            System.err.println("Ошибка при выполнении входа: " + e.getMessage());
        }
    }

    @Override
    public String getName() {
        return "login";
    }
}
