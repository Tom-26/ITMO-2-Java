package org.example;

import org.example.Commands.Realize.CommandExecutor;

import java.util.Scanner;

public class Console {
    private final CommandExecutor commandExecutor;
    private final Scanner scanner;

    public Console(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Интерактивная консоль управления коллекцией маршрутов. Введите 'help' для списка команд.");

        while (true) {
            System.out.print("Введите команду: ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) continue;

            String[] parts = input.split("\\s+", 2);
            String commandName = parts[0];
            String[] args = (parts.length > 1) ? parts[1].split("\\s+") : new String[0];

            if ("exit".equalsIgnoreCase(commandName)) {
                System.out.println("Завершение работы программы.");
                break;
            }

            executeCommand(commandName, args);
        }

        scanner.close();
    }

    private void executeCommand(String commandName, String[] args) {
        try {
            commandExecutor.executeCommand(commandName, args);
        } catch (Exception e) {
            System.err.println("Ошибка при выполнении команды: " + e.getMessage());
        }
    }
}
