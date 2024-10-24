package org.example.Commands.Realize;
import org.example.Commands.BaseInterfaceCommand;
import org.example.Managers.CollectionManager;
import org.example.models.Route;

    public class ShowCommand implements BaseInterfaceCommand {
        private CollectionManager collectionManager;

        public ShowCommand(CollectionManager collectionManager) {
            this.collectionManager = collectionManager;
        }

        @Override
        public void execute(String[] args) {
            if (collectionManager.getRoutes().isEmpty()) {
                System.out.println("Коллекция пуста.");
            } else {
                System.out.println("Все маршруты в коллекции:");
                for (Route route : collectionManager.getRoutes()) {
                    System.out.println(route);
                }
            }
        }

        @Override
        public String getName() {
            return "show";
        }
    }
