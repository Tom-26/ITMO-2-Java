package org.example.Commands.Realize;
import org.example.CollectionManager;
import org.example.Commands.BaseInterfaceCommand;

import java.util.Scanner;
    public class RemoveByIdCommand implements BaseInterfaceCommand {
        private final CollectionManager collectionManager;

        public RemoveByIdCommand(CollectionManager collectionManager) {
            this.collectionManager = collectionManager;
        }

        @Override
        public void execute(String[] args) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Введите ID маршрута для удаления: ");
            long id;

            try {
                id = Long.parseLong(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: ID должен быть числом.");
                return;
            }

            boolean isRemoved = collectionManager.removeRouteById(id);
            if (isRemoved) {
                System.out.println("Маршрут с ID " + id + " успешно удален.");
            } else {
                System.out.println("Маршрут с ID " + id + " не найден.");
            }
        }

        @Override
        public String getName() {
            return "remove_by_id";
        }
    }


