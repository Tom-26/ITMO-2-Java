package org.example.Commands.Realize;
import org.example.Commands.BaseInterfaceCommand;
import org.example.Managers.CollectionManager;

    public class ReorderCommand implements BaseInterfaceCommand {
        private final CollectionManager collectionManager;

        public ReorderCommand(CollectionManager collectionManager) {
            this.collectionManager = collectionManager;
        }

        @Override
        public void execute(String[] args) {
            if (collectionManager.getRoutes().isEmpty()) {
                System.out.println("Ошибка: Коллекция пуста, переупорядочивание невозможно.");
            } else {
                collectionManager.reorder();
                System.out.println("Коллекция была успешно переупорядочена в обратном порядке.");
            }
        }

        @Override
        public String getName() {
            return "reorder";
        }
    }

