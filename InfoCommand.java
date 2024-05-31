package org.example.Commands.Realize;
import org.example.Commands.BaseInterfaceCommand;
import org.example.CollectionManager;

public class InfoCommand implements BaseInterfaceCommand {
    private CollectionManager collectionManager;

    public InfoCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        System.out.println("Тип коллекции: " + collectionManager.getRoutes().getClass().getName());
        System.out.println("Дата инициализации коллекции: " + collectionManager.getInitializationDate());
        System.out.println("Количество элементов в коллекции: " + collectionManager.getRoutes().size());

    }
    @Override
    public String getName() {
        return "info";
    }
}