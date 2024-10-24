package org.example.Commands.Realize;
import org.example.CollectionManager;
import org.example.Commands.BaseInterfaceCommand;

public class HelpCommand implements BaseInterfaceCommand {
    public HelpCommand() {
    }

    public HelpCommand(CollectionManager collectionManager) {
    }

    @Override
    public void execute(String[] args) {
        for (HelpOut info : HelpOut.values()) {
            System.out.println(info.getCommandName() + ": " + info.getDescription());
        }
    }
    @Override
    public String getName() {
        return "help";
    }
}
