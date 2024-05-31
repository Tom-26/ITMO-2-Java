package org.example.Commands.Realize;
import org.example.Commands.BaseInterfaceCommand;
public class ExitCommand implements BaseInterfaceCommand {
    @Override
    public void execute(String[] args) {
        System.out.println("Выход из программы.");
        System.exit(0);  // Выход из программы с кодом 0 (успешное завершение)
    }
    @Override
    public String getName() {
        return "exit";
    }
}
