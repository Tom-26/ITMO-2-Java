package org.example.Commands.Realize;

import org.example.CollectionManager;
import org.example.Commands.BaseInterfaceCommand;
import org.example.Route;
import org.example.Coordinates;
import org.example.Location;

import java.time.ZonedDateTime;
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

        // Пример получения данных от пользователя:
        System.out.print("Введите название маршрута: ");
        String name = scanner.nextLine();

        // Продолжите запрос остальных данных аналогичным образом
        // Здесь для примера просто задаем некоторые значения
        Coordinates coordinates = new Coordinates(0, 0L); // Замените на реальные данные
        ZonedDateTime creationDate = ZonedDateTime.now();
        Location from = new Location(0f, 0f, "Начальная точка"); // Замените на реальные данные
        Location to = new Location(0f, 0f, "Конечная точка"); // Замените на реальные данные
        long distance = 100; // Замените на реальное значение

        // Создание и добавление нового маршрута
        Route newRoute = new Route(/* предполагается, что у вас есть конструктор со всеми необходимыми параметрами */);
        collectionManager.addRoute(newRoute);

        System.out.println("Маршрут успешно добавлен.");
    }

    @Override
    public String getName() {
        return "add";
    }
}
