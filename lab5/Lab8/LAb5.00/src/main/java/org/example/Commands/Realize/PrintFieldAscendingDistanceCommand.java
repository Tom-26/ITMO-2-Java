package org.example.Commands.Realize;
import org.example.Commands.BaseInterfaceCommand;
import org.example.Managers.CollectionManager;
import org.example.models.Route;

import java.util.Comparator;
public class PrintFieldAscendingDistanceCommand implements BaseInterfaceCommand{
    private final CollectionManager collectionManager;

    public PrintFieldAscendingDistanceCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        if (collectionManager.getRoutes().isEmpty()) {
            System.out.println("Коллекция пуста.");
        } else {
            collectionManager.getRoutes().stream()
                    .sorted(Comparator.comparing(Route::getDistance))
                    .forEach(route -> System.out.println("Route ID: " + route.getId() + ", Distance: " + route.getDistance()));
            System.out.println("Все дистанции выведены в порядке возрастания.");
        }
    }

    @Override
    public String getName() {
        return "print_field_ascending_distance";
    }
}
