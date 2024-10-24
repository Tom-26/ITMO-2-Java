package org.example;

import org.example.Commands.BaseInterfaceCommand;
import org.example.Commands.Realize.*;
import org.example.models.User;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class ConsoleCommandExecutor {
    private final Map<String, BaseInterfaceCommand> commands = new HashMap<>();
    private final Connection connection;
    private final CollectionManager collectionManager;
    private User currentUser;

    public ConsoleCommandExecutor(Connection connection, CollectionManager collectionManager) {
        this.connection = connection;
        this.collectionManager = collectionManager;
        registerCommands();
    }

    private void registerCommands() {
        registerCommand(new RegisterCommand(connection, this));
        registerCommand(new LoginCommand(connection, this));
        registerCommand(new AddCommand(connection, collectionManager));
        registerCommand(new InfoCommand(collectionManager));
        registerCommand(new HelpCommand(collectionManager));
        registerCommand(new ClearCommand(collectionManager));
        registerCommand(new CountGreaterThanDistanceCommand(collectionManager));
        registerCommand(new MaxByIdCommand(collectionManager));
        registerCommand(new PrintFieldAscendingDistanceCommand(collectionManager));
        registerCommand(new ReorderCommand(collectionManager));
        registerCommand(new RemoveAtCommand(collectionManager));
        registerCommand(new UpdateByIdCommand(collectionManager));
        registerCommand(new RemoveByIdCommand(collectionManager));
        registerCommand(new RemoveLastCommand(collectionManager));
        registerCommand(new ShowCommand(collectionManager));
        registerCommand(new SaveCommand(collectionManager));
    }

    private void registerCommand(BaseInterfaceCommand command) {
        commands.put(command.getName(), command);
    }

    public void executeCommand(String commandName, String[] args) {
        if (commandName.equals("login") || commandName.equals("register") || isUserLoggedIn()) {
            BaseInterfaceCommand command = commands.get(commandName);
            if (command != null) {
                command.execute(args);
            } else {
                System.out.println("Неизвестная команда: " + commandName);
            }
        } else {
            System.out.println("Ошибка: необходимо зарегистрироваться и войти в систему перед выполнением команд.");
        }
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public boolean isUserLoggedIn() {
        return currentUser != null;
    }
}

