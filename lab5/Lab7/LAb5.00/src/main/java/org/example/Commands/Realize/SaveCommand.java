package org.example.Commands.Realize;

import org.example.Commands.BaseInterfaceCommand;
import org.example.CollectionManager;

import java.sql.Connection;
import java.sql.SQLException;

public class SaveCommand implements BaseInterfaceCommand {
    private final CollectionManager collectionManager;

    public SaveCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        try {
            collectionManager.saveCollectionToDatabase();
            System.out.println("Коллекция успешно сохранена.");
        } catch (SQLException e) {
            System.err.println("Ошибка при сохранении коллекции: " + e.getMessage());
        }
    }

    @Override
    public String getName() {
        return "save";
    }
}
