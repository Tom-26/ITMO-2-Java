package org.example.Commands.Realize;
import org.example.CollectionManager;
import org.example.Commands.BaseInterfaceCommand;

public class ClearCommand implements BaseInterfaceCommand {
    private final CollectionManager collectionManager;

    public ClearCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        collectionManager.clearRoutes();
        System.out.println("Все маршруты были удалены из коллекции.");
    }

    @Override
    public String getName() {
        return "clear";
    }
}
