package org.example.Commands.Realize;
import org.example.Commands.BaseInterfaceCommand;
import org.example.CollectionManager;
import java.io.IOException;
public class SaveCommand implements BaseInterfaceCommand {
    private final CollectionManager collectionManager;

    public SaveCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        try {
            collectionManager.saveCollection();  // Попытка сохранения коллекции
            System.out.println("Коллекция успешно сохранена.");
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении коллекции: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Непредвиденная ошибка: " + e.getMessage());
        }
    }

    @Override
    public String getName() {
        return "save";
    }
}
