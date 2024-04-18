package org.example.Commands;

public interface BaseInterfaceCommand {
    void execute(String[] args); // method that init using command
    String getName();  // method that get name for dinamic register and thretment
}