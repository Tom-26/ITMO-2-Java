package org.example.Commands.Realize;
public enum HelpOut {
    DESCRIPTIONINFO ("info", "Выводит стандатный поток вывода данных о коллекции, таких как тип, дата инициализации, количество элементов и тп"),
    DESCRIPTIONADD ("add {element}", "Добавляет новый элемент в коллекцию"),
    DESCRIPTIONREMOVEBYID ("remove/update","Для удаления или обновления элементов нажмите на них в таблице и выберите действие(белое изменить/синие удалить)"),
    DESCRIPTIONCLEAR ("clear","Очищает коллекцию"),
    DESCRIPTIONSAVE ("save changes","Сохраняет коллекцию в файл"),
    DESCRIPTIONEXIT ("exit","Завершает программу (без сохранения)"),
    DESCRIPTIONREORDER ("reorder","Отсортирует коллекцию в порядке, обратном нынешнему"),
    DESCRIPTIONMAXBYID ("max_by_id","Выводит любой объект из коллекции, значение поля id котрого является максимальным"),
    DESCRIPTIONCOUNTGREATERTHANDISTANCE ("count_greater_than_distance distance","Выводит количество элементов, значение поля distance которых больше заданного"),
    DESCRIPTIONPRINTFIELDASCENDINGDISTANCE ("print_field_ascending_distance","Выводит значение поля distance всех элементов в порядке возрастания");
     private final String description;
     private final String commandName;
    HelpOut(String commandName, String description){
        this.commandName=commandName;
        this.description=description;
    }

    public String getDescription() {
        return description;
    }

    public String getCommandName() {
        return commandName;
    }
}
