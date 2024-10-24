package org.example.Commands.Realize;

import org.example.CollectionManager;
import org.example.Commands.BaseInterfaceCommand;

public class RemoveAtCommand implements BaseInterfaceCommand {private final CollectionManager collectionManager;

    public RemoveAtCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 1) {
            System.out.println("Ошибка: Необходимо указать один аргумент (индекс элемента).");
            return;
        }

        int index;
        try {
            index = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: Индекс должен быть целым числом.");
            return;
        }

        if (index < 0 || index >= collectionManager.getRoutes().size()) {
            System.out.println("Ошибка: Индекс вне допустимого диапазона. Допустимый диапазон: 0 - " +
                    (collectionManager.getRoutes().size() - 1));
            return;
        }

        collectionManager.removeAt(index);
        System.out.println("Элемент на позиции " + index + " успешно удален.");
    }

    @Override
    public String getName() {
        return "remove_at";
    }
}
