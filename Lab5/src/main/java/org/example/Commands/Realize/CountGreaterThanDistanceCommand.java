package org.example.Commands.Realize;
import org.example.Commands.BaseInterfaceCommand;
import org.example.CollectionManager;
public class CountGreaterThanDistanceCommand implements BaseInterfaceCommand{
    private final CollectionManager collectionManager;

    public CountGreaterThanDistanceCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 1) {
            System.out.println("Ошибка: Необходимо указать один аргумент (значение distance).");
            return;
        }

        long distance;
        try {
            distance = Long.parseLong(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: аргумент distance должен быть числом.");
            return;
        }

        long count = collectionManager.getRoutes().stream()
                .filter(route -> route.getDistance() > distance)
                .count();

        System.out.println("Количество маршрутов с distance больше " + distance + ": " + count);
    }

    @Override
    public String getName() {
        return "count_greater_than_distance";
    }

}
