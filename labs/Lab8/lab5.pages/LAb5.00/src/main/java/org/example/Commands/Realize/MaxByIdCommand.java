package org.example.Commands.Realize;
import org.example.Commands.BaseInterfaceCommand;
import org.example.CollectionManager;
import org.example.models.Route;
import java.util.Comparator;
    public class MaxByIdCommand implements BaseInterfaceCommand {
        private final CollectionManager collectionManager;
        public MaxByIdCommand(CollectionManager collectionManager) {
            this.collectionManager = collectionManager;
        }

        @Override
        public void execute(String[] args) {
            if (collectionManager.getRoutes().isEmpty()) {
                System.out.println("Коллекция пуста.");
            } else {
                Route maxByIdRoute = collectionManager.getRoutes().stream()
                        .max(Comparator.comparing(Route::getId))
                        .orElse(null); // Это условие никогда не должно выполняться, так как коллекция не пуста

                if (maxByIdRoute != null) {
                    System.out.println("Элемент с максимальным ID: " + maxByIdRoute);
                } else {
                    System.out.println("Не удалось найти элемент с максимальным ID.");
                }
            }
        }

        @Override
        public String getName() {
            return "max_by_id";
        }
    }


