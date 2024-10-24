package org.example.Commands;

import java.io.IOException;

public interface BaseInterfaceCommand {
    void execute(String[] args) throws IOException; // method that init using command
    String getName();  // method that get name for dinamic register and thretment
}