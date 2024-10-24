package org.example.Commands.Realize;
import org.example.CollectionManager;
import org.example.Commands.BaseInterfaceCommand;
public class RemoveLastCommand implements BaseInterfaceCommand {
    private final CollectionManager collectionManager;

    public RemoveLastCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        if (collectionManager.getRoutes().isEmpty()) {
            System.out.println("Ошибка: Коллекция уже пуста.");
        } else {
            collectionManager.removeLast();
            System.out.println("Последний элемент коллекции успешно удален.");
        }
    }

    @Override
    public String getName() {
        return "remove_last";
    }
}
