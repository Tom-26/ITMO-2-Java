package org.example.Commands.Realize;

import org.example.Managers.CollectionManager;
import org.example.Commands.BaseInterfaceCommand;
import org.example.ConsoleBuisness.CommandExecutorInterface;

public class RemoveLastCommand implements BaseInterfaceCommand {
    private final CollectionManager collectionManager;
    private final CommandExecutorInterface commandExecutor;

    public RemoveLastCommand(CollectionManager collectionManager, CommandExecutorInterface commandExecutor) {
        this.collectionManager = collectionManager;
        this.commandExecutor = commandExecutor;
    }

    @Override
    public void execute(String[] args) {
        if (!commandExecutor.isUserLoggedIn()) {
            System.out.println("Ошибка: необходимо авторизоваться перед выполнением команды.");
            return;
        }
        int userId = commandExecutor.getCurrentUser().getId();

        if (collectionManager.getRoutes().isEmpty()) {
            System.out.println("Ошибка: Коллекция уже пуста.");
        } else {
            collectionManager.removeLast(userId);

        }
    }

    @Override
    public String getName() {
        return "remove_last";
    }
}
