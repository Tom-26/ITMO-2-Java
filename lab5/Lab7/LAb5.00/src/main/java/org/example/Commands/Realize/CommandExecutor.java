package org.example.Commands.Realize;

import org.example.CollectionManager;
import org.example.CommandExecutorInterface;
import org.example.Commands.BaseInterfaceCommand;
import org.example.models.User;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class CommandExecutor implements CommandExecutorInterface {
    private final CollectionManager collectionManager;
    private final Map<String, BaseInterfaceCommand> commands = new HashMap<>();
    private final Connection connection;
    private User currentUser;  // Для отслеживания текущего пользователя

    public CommandExecutor(Connection connection, CollectionManager collectionManager) {
        this.connection = connection;
        this.collectionManager = collectionManager;
        registerCommands();
    }

    private void registerCommands() {
        commands.put("help", new HelpCommand(collectionManager));
        commands.put("info", new InfoCommand(collectionManager));
        commands.put("add", new AddCommand(connection, collectionManager)); // Ensure to manage the connection
        commands.put("show", new ShowCommand(collectionManager));
        commands.put("clear", new ClearCommand(collectionManager, this));
        commands.put("save", new SaveCommand(collectionManager));
        commands.put("update_id", new UpdateByIdCommand(collectionManager));
        commands.put("remove_by_id", new RemoveByIdCommand(collectionManager, this));
        commands.put("remove_at", new RemoveAtCommand(collectionManager, this));
        commands.put("remove_last", new RemoveLastCommand(collectionManager, this));
        commands.put("reorder", new ReorderCommand(collectionManager));
        commands.put("max_by_id", new MaxByIdCommand(collectionManager));
        commands.put("count_greater_than_distance", new CountGreaterThanDistanceCommand(collectionManager));
        commands.put("print_field_ascending_distance", new PrintFieldAscendingDistanceCommand(collectionManager));
        commands.put("execute_script", new ExecuteScriptCommand(this));
        commands.put("exit", new ExitCommand());
        commands.put("register", new RegisterCommand(connection, this));
        commands.put("login", new LoginCommand(connection, this));  // Передаем CommandExecutor для установки текущего пользователя
    }

    public Map<String, BaseInterfaceCommand> getCommands() {
        return commands;
    }

    public void executeCommand(String commandName, String[] args) {
        if (commandName.equals("login") || commandName.equals("register") || currentUser != null) {  // Проверка авторизации
            BaseInterfaceCommand command = commands.get(commandName.toLowerCase());
            if (command == null) {
                System.out.println("Команда не найдена: " + commandName);
                return;
            }
            try {
                command.execute(args);
            } catch (Exception e) {
                System.err.println("Произошла ошибка: " + e.getMessage());
            }
        } else {
            System.out.println("Ошибка: необходимо авторизоваться перед выполнением команд.");
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

    public CollectionManager getCollectionManager() {
        return collectionManager;
    }
}
