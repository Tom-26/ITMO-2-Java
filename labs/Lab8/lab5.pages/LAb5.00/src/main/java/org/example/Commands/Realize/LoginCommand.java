package org.example.Commands.Realize;

import org.example.Commands.BaseInterfaceCommand;
import org.example.models.User;
import org.example.services.AuthService;
import org.example.ConsoleCommandExecutor;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;

public class LoginCommand implements BaseInterfaceCommand {
    private final AuthService authService;
    private final ConsoleCommandExecutor commandExecutor;

    public LoginCommand(Connection connection, ConsoleCommandExecutor commandExecutor) {
        this.authService = new AuthService(connection);
        this.commandExecutor = commandExecutor;
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 2) {
            System.out.println("Недостаточно аргументов для входа. Введите имя пользователя и пароль.");
            return;
        }

        String username = args[0];
        String password = args[1];

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

