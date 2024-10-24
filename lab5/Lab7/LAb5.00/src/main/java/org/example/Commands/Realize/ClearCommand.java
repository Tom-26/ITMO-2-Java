package org.example.Commands.Realize;

import org.example.CollectionManager;
import org.example.Commands.BaseInterfaceCommand;
import org.example.CommandExecutorInterface;

public class ClearCommand implements BaseInterfaceCommand {
    private final CollectionManager collectionManager;
    private final CommandExecutorInterface commandExecutor;

    public ClearCommand(CollectionManager collectionManager, CommandExecutorInterface commandExecutor) {
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
        collectionManager.clearRoutes(userId);
    }

    @Override
    public String getName() {
        return "clear";
    }
}
