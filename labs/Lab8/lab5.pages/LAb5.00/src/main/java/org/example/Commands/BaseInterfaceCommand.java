package org.example.Commands;

public interface BaseInterfaceCommand {
    void execute(String[] args); // Удаляем throws SQLException
    String getName();
}
