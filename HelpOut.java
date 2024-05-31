package org.example.Commands.Realize;
public enum HelpOut {
    DESCRIPTIONHELP ("help","Выводит справку по доступным командам"),
    DESCRIPTIONINFO ("info", "Выводит стандатный поток вывода данных о коллекции, таких как тип, дата инициализации, количество элементов и тп"),
    DESCRIPTOINSHOW ("show","Выводит в стандартный поток вывода все элементы коллекции"),
    DESCRIPTIONADD ("add {element}", "Добавляет новый элемент в коллекцию"),
    DESCRIPTIONREMOVEBYID ("remove_by_id id","Удаляет элемент по его id"),
    DESCRIPTIONUPDATEID ("update id {element}", "Обновляет значение элемента коллекции, id которого равен заданному"),
    DESCRIPTIONCLEAR ("clear","Очищает коллекцию"),
    DESCRIPTIONSAVE ("save","Сохраняет коллекцию в файл"),
    DESCRIPTIONEXECUTESCRIPT ("execute_script file_name","Считает и исполняет скрипт из указаного файла. В скрите содержаться команды в таком же виде, в котором их вводит пользователь в интерактивном режиме(Глубина рекурсии - 5 вызовов)"),
    DESCRIPTIONEXIT ("exit","Завершает программу (без сохранения)"),
    DESCRIPTIONREMOVEAT ("remove_at index","Удаляет элемент, находящийся в позиции коллекци (index)"),
    DESCRIPTIONREMOVELAST ("remove_last","Удаляет последний элемент в коллекции"),
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
