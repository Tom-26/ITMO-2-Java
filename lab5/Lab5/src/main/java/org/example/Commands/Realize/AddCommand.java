package org.example.Commands.Realize;

import org.example.CollectionManager;
import org.example.Commands.BaseInterfaceCommand;
import org.example.Route;
import org.example.Coordinates;
import org.example.Location;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AddCommand implements BaseInterfaceCommand {
    private final CollectionManager collectionManager;

    public AddCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Добавление нового маршрута.");
        String name = promptForString(scanner, "Введите название маршрута: ", true);

        float x = promptForFloat(scanner, "Введите координату X (float) для координат: ");
        long y = promptForLong(scanner, "Введите координату Y (long) для координат: ", true);
        Coordinates coordinates = new Coordinates(x, y);

        System.out.println("Введите данные для начальной локации:");
        Location from = readLocation(scanner);

        System.out.println("Введите данные для конечной локации:");
        Location to = readLocation(scanner);

        long distance = promptForLong(scanner, "Введите дистанцию маршрута (long): ", false);
        String creationDate = DateTimeFormatter.ISO_ZONED_DATE_TIME.format(ZonedDateTime.now());
        Route newRoute = new Route(name, coordinates, creationDate, from, to, distance);
        collectionManager.addRoute(newRoute);

        System.out.println("Маршрут успешно добавлен.");
    }

    private String promptForString(Scanner scanner, String message, boolean nonEmpty) {
        String input;
        do {
            System.out.print(message);
            input = scanner.nextLine().trim();
            if (nonEmpty && input.isEmpty()) {
                System.out.println("Это поле не может быть пустым.");
            } else {
                break;
            }
        } while (true);
        return input;
    }

    private float promptForFloat(Scanner scanner, String message) {
        float value;
        while (true) {
            System.out.print(message);
            try {
                value = scanner.nextFloat();
                scanner.nextLine();  // Очистка буфера после числа
                break;
            } catch (InputMismatchException e) {
                System.out.println("Ошибка: введенные данные не являются числом типа float.");
                scanner.nextLine();  // Очистка буфера
            }
        }
        return value;
    }

    private long promptForLong(Scanner scanner, String message, boolean positive) {
        long value;
        while (true) {
            System.out.print(message);
            try {
                value = scanner.nextLong();
                scanner.nextLine();  // Очистка буфера после числа
                if (positive && value <= 0) {
                    System.out.println("Ошибка: число должно быть больше 0.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Ошибка: введенные данные не являются числом типа long.");
                scanner.nextLine();  // Очистка буфера
            }
        }
        return value;
    }

    private Location readLocation(Scanner scanner) {
        float locX = promptForFloat(scanner, "Введите координату X (float) для локации: ");
        float locY = promptForFloat(scanner, "Введите координату Y (float) для локации: ");
        String locName = promptForString(scanner, "Введите название локации (может быть пустым): ", false);
        return new Location(locX, locY, locName.isEmpty() ? null : locName);
    }

    @Override
    public String getName() {
        return "add";
    }
}
