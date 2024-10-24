package org.example;

import org.example.Commands.Realize.CommandExecutor;
import java.nio.file.*;
import java.io.IOException;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) throws IOException {
        String jsonFilesPath = System.getenv("JSON_FILES_PATH");
        if (jsonFilesPath == null) {
            System.err.println("Переменная окружения 'JSON_FILES_PATH' не установлена.");
            return;
        }

        Path dirPath = Paths.get(jsonFilesPath);
        /*if (!Files.isDirectory(dirPath)) {
            System.err.println("Указанный путь не является директорией: " + jsonFilesPath);
            return;
        }
        if (!Files.exists(dirPath)){
            Files.createDirectories(dirPath.getParent());
        }
*/
        AuthenticationService authService = null;
        CollectionManager collectionManager = new CollectionManager(authService);
        CommandExecutor commandExecutor = new CommandExecutor(collectionManager);
        Console console = new Console(commandExecutor);
        console.start();
    }
}
