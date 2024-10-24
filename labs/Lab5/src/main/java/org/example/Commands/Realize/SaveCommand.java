package org.example.Commands.Realize;

import org.example.CollectionManager;
import org.example.Commands.BaseInterfaceCommand;

import java.io.IOException;

public class SaveCommand implements BaseInterfaceCommand {
    private final CollectionManager collectionManager;

    public SaveCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) throws IOException {
        collectionManager.saveCollection();  // Этот метод выбрасывает IOException
        System.out.println("Коллекция успешно сохранена.");
    }

    @Override
    public String getName() {
        return "save";
    }
}
