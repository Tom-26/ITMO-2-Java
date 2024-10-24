package org.example;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws IOException, SQLException {
        Connection connection = DatabaseManager.getConnection();
        CollectionManager collectionManager = new CollectionManager(connection);
        ConsoleCommandExecutor consoleCommandExecutor = new ConsoleCommandExecutor(connection, collectionManager);
        Console console = new Console(consoleCommandExecutor);
        console.start();
    }
}
