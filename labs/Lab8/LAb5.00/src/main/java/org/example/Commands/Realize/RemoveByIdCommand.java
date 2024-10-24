package org.example.Commands.Realize;

import org.example.Managers.CollectionManager;
import org.example.Commands.BaseInterfaceCommand;
import org.example.ConsoleBuisness.CommandExecutorInterface;

import java.util.Scanner;

public class RemoveByIdCommand implements BaseInterfaceCommand {
    private final CollectionManager collectionManager;
    private final CommandExecutorInterface commandExecutor;

    public RemoveByIdCommand(CollectionManager collectionManager, CommandExecutorInterface commandExecutor) {
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

        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите ID маршрута для удаления: ");
        long id;

        try {
            id = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: ID должен быть числом.");
            return;
        }

        boolean isRemoved = collectionManager.removeRouteById(id, userId);
        if (isRemoved) {
            System.out.println("Маршрут с ID " + id + " успешно удален.");
        } else {
            System.out.println("Маршрут с ID " + id + " не найден или у вас нет прав на его удаление.");
        }
    }

    @Override
    public String getName() {
        return "remove_by_id";
    }
}
