package org.example.Commands.Realize;

import org.example.Commands.BaseInterfaceCommand;
import org.example.models.User;
import org.example.ConsoleCommandExecutor;

import java.util.Map;

public class CommandExecutor {
    private final ConsoleCommandExecutor consoleCommandExecutor;

    public CommandExecutor(ConsoleCommandExecutor consoleCommandExecutor) {
        this.consoleCommandExecutor = consoleCommandExecutor;
    }

    public void setCurrentUser(User user) {
        consoleCommandExecutor.setCurrentUser(user);
    }


    public void executeCommand(String commandName, String[] args) {
        consoleCommandExecutor.executeCommand(commandName, args);
    }
}
