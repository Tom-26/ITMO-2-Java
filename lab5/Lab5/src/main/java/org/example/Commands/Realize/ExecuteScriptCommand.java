package org.example.Commands.Realize;

import org.example.Commands.BaseInterfaceCommand;
import org.example.Commands.Realize.CommandExecutor;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ExecuteScriptCommand implements BaseInterfaceCommand {
    private final CommandExecutor commandExecutor;
    private static int recursionDepth = 0;
    private static final int MAX_RECURSION_DEPTH = 5;

    public ExecuteScriptCommand(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 1) {
            System.out.println("Ошибка: Необходимо указать один аргумент (имя файла).");
            return;
        }

        if (recursionDepth >= MAX_RECURSION_DEPTH) {
            System.out.println("Ошибка: Превышена максимальная глубина рекурсии для execute_script.");
            return;
        }

        String filePath = args[0];
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
            return;
        }

        recursionDepth++;  // Увеличиваем глубину рекурсии
        System.out.println("Выполнение команд из файла: " + filePath);
        for (String line : lines) {
            if (line.trim().isEmpty() || line.startsWith("#")) { // Пропуск пустых строк и комментариев
                continue;
            }

            String[] parts = line.trim().split("\\s+", 2);
            String commandName = parts[0];
            String[] commandArgs = (parts.length > 1) ? parts[1].split("\\s+") : new String[0];

            System.out.println(">>> " + line);
            commandExecutor.executeCommand(commandName, commandArgs); // Выполнение команды
        }
        recursionDepth--;  // Уменьшаем глубину рекурсии после выполнения скрипта
    }

    @Override
    public String getName() {
        return "execute_script";
    }
}
