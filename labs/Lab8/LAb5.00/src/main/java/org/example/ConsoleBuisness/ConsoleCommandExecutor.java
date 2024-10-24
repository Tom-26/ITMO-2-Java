package org.example.ConsoleBuisness;

import org.example.Managers.CollectionManager;
import org.example.Commands.BaseInterfaceCommand;
import org.example.Commands.Realize.*;
import org.example.models.User;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class ConsoleCommandExecutor implements CommandExecutorInterface {
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
        commands.put("register", new RegisterCommand(connection, this));
        commands.put("login", new LoginCommand(connection, this));
        commands.put("add", new AddCommand(connection, collectionManager));
        commands.put("info", new InfoCommand(collectionManager));
        commands.put("help", new HelpCommand(collectionManager));
        commands.put("clear", new ClearCommand(collectionManager, this));
        commands.put("count_greater_than_distance", new CountGreaterThanDistanceCommand(collectionManager));
        commands.put("max_by_id", new MaxByIdCommand(collectionManager));
        commands.put("print_field_ascending_distance", new PrintFieldAscendingDistanceCommand(collectionManager));
        commands.put("reorder", new ReorderCommand(collectionManager));
        commands.put("remove_at", new RemoveAtCommand(collectionManager, this));
        commands.put("update_id", new UpdateByIdCommand(collectionManager));
        commands.put("remove_by_id", new RemoveByIdCommand(collectionManager, this));
        commands.put("remove_last", new RemoveLastCommand(collectionManager, this));
        commands.put("show", new ShowCommand(collectionManager));
        commands.put("save", new SaveCommand(collectionManager));
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

    @Override
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    @Override
    public boolean isUserLoggedIn() {
        return currentUser != null;
    }

    @Override
    public User getCurrentUser() {
        return currentUser;
    }

    public Map<String, BaseInterfaceCommand> getCommands() {
        return commands;
    }
}
