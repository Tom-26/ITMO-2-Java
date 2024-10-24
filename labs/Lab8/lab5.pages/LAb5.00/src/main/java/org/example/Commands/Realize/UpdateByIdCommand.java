package org.example.Commands.Realize;
import org.example.Commands.BaseInterfaceCommand;
import org.example.CollectionManager;
import org.example.models.Route;
import org.example.models.Coordinates;
import org.example.models.Location;
import java.util.Scanner;
public class UpdateByIdCommand implements BaseInterfaceCommand {
    private final CollectionManager collectionManager;
    public UpdateByIdCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }
    @Override
    public void execute(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите ID маршрута для обновления: ");
        long id;
        try {
            id = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: ID должен быть числом.");
            return;
        }
        Route routeToUpdate = collectionManager.getRoutes().stream()
                .filter(route -> route.getId().equals(id))
                .findFirst()
                .orElse(null);
        if (routeToUpdate == null) {
            System.out.println("Маршрут с ID " + id + " не найден.");
            return;
        }
        System.out.println("Введите новые данные для маршрута:");
        System.out.print("Название маршрута: ");
        String name = scanner.nextLine();
        System.out.print("Координаты x: ");
        float x = Float.parseFloat(scanner.nextLine());
        System.out.print("Координаты y: ");
        long y = Long.parseLong(scanner.nextLine());
        System.out.print("Откуда (название): ");
        String fromName = scanner.nextLine();
        System.out.print("Откуда (x): ");
        Float fromX = Float.parseFloat(scanner.nextLine());
        System.out.print("Откуда (y): ");
        Float fromY = Float.parseFloat(scanner.nextLine());
        System.out.print("Куда (название): ");
        String toName = scanner.nextLine();
        System.out.print("Куда (x): ");
        Float toX = Float.parseFloat(scanner.nextLine());
        System.out.print("Куда (y): ");
        Float toY = Float.parseFloat(scanner.nextLine());
        System.out.print("Дистанция: ");
        long distance = Long.parseLong(scanner.nextLine());
        // Updating the route
        routeToUpdate.setName(name);
        routeToUpdate.setCoordinates(new Coordinates(x, y));
        routeToUpdate.setFrom(new Location(fromX, fromY, fromName));
        routeToUpdate.setTo(new Location(toX, toY, toName));
        routeToUpdate.setDistance(distance);
        System.out.println("Маршрут с ID " + id + " успешно обновлен.");
    }
    @Override
    public String getName() {
        return "update_id";
    }
}