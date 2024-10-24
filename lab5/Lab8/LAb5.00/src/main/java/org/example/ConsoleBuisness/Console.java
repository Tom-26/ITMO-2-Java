package org.example.ConsoleBuisness;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Console {
    private final ConsoleCommandExecutor commandExecutor;
    private final Scanner scanner;

    public Console(ConsoleCommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Интерактивная консоль управления коллекцией маршрутов. Введите 'register' для регистрации или 'login' для входа.");

        while (true) {
            try {
                System.out.print("Введите команду: ");
                String input;
                try {
                    input = scanner.nextLine().trim();
                } catch (NoSuchElementException e) {
                    System.out.println("Конец ввода (Ctrl + D). Завершение работы программы.");
                    break;
                }

                if (input.isEmpty()) continue;

                String[] parts = input.split("\\s+", 2);
                String commandName = parts[0];
                String[] args = (parts.length > 1) ? parts[1].split("\\s+") : new String[0];

                if ("exit".equalsIgnoreCase(commandName)) {
                    System.out.println("Завершение работы программы.");
                    break;
                }

                commandExecutor.executeCommand(commandName, args);
            } catch (Exception e) {
                System.err.println("Ошибка при выполнении команды: " + e.getMessage());
            }
        }

        scanner.close();
    }
}
