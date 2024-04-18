package org.example;

import org.example.Commands.Realize.CommandExecutor;
import java.nio.file.*;
import java.io.IOException;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        String jsonFilesPath = System.getenv("JSON_FILES_PATH");
        if (jsonFilesPath == null) {
            System.err.println("Переменная окружения 'JSON_FILES_PATH' не установлена.");
            return;
        }

        Path dirPath = Paths.get(jsonFilesPath);
        if (!Files.exists(dirPath) || !Files.isDirectory(dirPath)) {
            System.err.println("Указанный путь не существует или не является директорией: " + jsonFilesPath);
            return;
        }

        LinkedList<Route> routes = new LinkedList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dirPath, "*.json")) {
            for (Path filePath : stream) {
                try {
                    Route route = FileUtil.readFromJson(filePath.toString(), Route.class);
                    routes.add(route);
                    System.out.println("Загружен маршрут из файла: " + filePath.getFileName());
                } catch (IOException e) {
                    System.err.println("Ошибка при чтении файла " + filePath + ": " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка при доступе к файлам в директории: " + e.getMessage());
        }

        CollectionManager collectionManager = new CollectionManager(routes);
        CommandExecutor commandExecutor = new CommandExecutor(collectionManager);
        Console console = new Console(commandExecutor);
        console.start();
    }
}
