package org.example.Commands.Realize;

import org.example.CollectionManager;
import org.example.Commands.BaseInterfaceCommand;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CommandExecutor {
    private final CollectionManager collectionManager;
    private final Map<String, BaseInterfaceCommand> commands = new HashMap<>();

    public CommandExecutor(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
        registerCommands();
    }

    private void registerCommands() {
        commands.put("help", new HelpCommand(collectionManager));
        commands.put("info", new InfoCommand(collectionManager));
        commands.put("add", new AddCommand(collectionManager));
        commands.put("show", new ShowCommand(collectionManager));
        commands.put("clear", new ClearCommand(collectionManager));
        commands.put("save", new SaveCommand(collectionManager));
        commands.put("update_id", new UpdateByIdCommand(collectionManager));
        commands.put("remove_by_id", new RemoveByIdCommand(collectionManager));
        commands.put("remove_at", new RemoveAtCommand(collectionManager));
        commands.put("remove_last", new RemoveLastCommand(collectionManager));
        commands.put("reorder", new ReorderCommand(collectionManager));
        commands.put("max_by_id", new MaxByIdCommand(collectionManager));
        commands.put("count_greater_than_distance", new CountGreaterThanDistanceCommand(collectionManager));
        commands.put("print_field_ascending_distance", new PrintFieldAscendingDistanceCommand(collectionManager));
        commands.put("execute_script", new ExecuteScriptCommand(this));
        commands.put("exit", new ExitCommand());
    }

    public void executeCommand(String commandName, String[] args) {
        BaseInterfaceCommand command = commands.get(commandName.toLowerCase());
        if (command == null) {
            System.out.println("Команда не найдена: " + commandName);
            return;
        }
        try {
            command.execute(args);
        } catch (IOException e) {
            System.err.println("Ошибка ввода/вывода при выполнении команды: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Произошла ошибка: " + e.getMessage());
        }
    }
}
