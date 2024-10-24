package org.example;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final CollectionManager collectionManager;

    public ClientHandler(Socket clientSocket, CollectionManager collectionManager) {
        this.clientSocket = clientSocket;
        this.collectionManager = collectionManager;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            out.println("Добро пожаловать на сервер. Введите команду:");

            String input;
            while ((input = in.readLine()) != null) {
                String response = handleCommand(input);
                out.println(response);
                if ("exit".equalsIgnoreCase(input.trim())) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String handleCommand(String input) {
        String[] parts = input.split(" ");
        String command = parts[0].toLowerCase();

        switch (command) {
            case "register":
                if (parts.length == 3) {
                    boolean success = collectionManager.register(parts[1], parts[2]);
                    return success ? "Регистрация успешна" : "Регистрация не удалась";
                } else {
                    return "Неправильный формат команды. Используйте: register <username> <password>";
                }
            case "login":
                if (parts.length == 3) {
                    boolean success = collectionManager.login(parts[1], parts[2]);
                    return success ? "Вход выполнен успешно" : "Вход не удался";
                } else {
                    return "Неправильный формат команды. Используйте: login <username> <password>";
                }
            case "logout":
                collectionManager.logout();
                return "Выход выполнен успешно";
            case "add":
                if (parts.length == 8) {
                    try {
                        String name = parts[1];
                        float coordX = Float.parseFloat(parts[2]);
                        long coordY = Long.parseLong(parts[3]);
                        Coordinates coordinates = new Coordinates(coordX, coordY);

                        float fromX = Float.parseFloat(parts[4]);
                        float fromY = Float.parseFloat(parts[5]);
                        Location from = new Location(fromX, fromY, null); // Без имени

                        float toX = Float.parseFloat(parts[6]);
                        float toY = Float.parseFloat(parts[7]);
                        Location to = new Location(toX, toY, null); // Без имени

                        long distance = Long.parseLong(parts[8]);

                        Route route = new Route(name, coordinates, null, from, to, distance);
                        collectionManager.addRoute(route);
                        return "Маршрут добавлен";
                    } catch (NumberFormatException e) {
                        return "Неправильный формат числа";
                    }
                } else {
                    return "Неправильный формат команды. Используйте: add <name> <coord_x> <coord_y> <from_x> <from_y> <to_x> <to_y> <distance>";
                }
            case "remove":
                if (parts.length == 2) {
                    try {
                        long id = Long.parseLong(parts[1]);
                        boolean success = collectionManager.removeRouteById(id);
                        return success ? "Маршрут удален" : "Маршрут не найден или принадлежит другому пользователю";
                    } catch (NumberFormatException e) {
                        return "Неправильный формат числа";
                    }
                } else {
                    return "Неправильный формат команды. Используйте: remove <id>";
                }
            case "clear":
                collectionManager.clearRoutes();
                return "Все маршруты удалены";
            case "show":
                return collectionManager.getRoutes().toString();
            case "reorder":
                collectionManager.reorder();
                return "Коллекция переупорядочена";
            case "update":
                if (parts.length == 9) {
                    try {
                        long id = Long.parseLong(parts[1]);
                        String name = parts[2];
                        float coordX = Float.parseFloat(parts[3]);
                        long coordY = Long.parseLong(parts[4]);
                        Coordinates coordinates = new Coordinates(coordX, coordY);

                        float fromX = Float.parseFloat(parts[5]);
                        float fromY = Float.parseFloat(parts[6]);
                        Location from = new Location(fromX, fromY, null); // Без имени

                        float toX = Float.parseFloat(parts[7]);
                        float toY = Float.parseFloat(parts[8]);
                        Location to = new Location(toX, toY, null); // Без имени

                        long distance = Long.parseLong(parts[9]);

                        Route route = new Route(name, coordinates, null, from, to, distance);
                        boolean success = collectionManager.updateRoute(id, route);
                        return success ? "Маршрут обновлен" : "Не удалось обновить маршрут";
                    } catch (NumberFormatException e) {
                        return "Неправильный формат числа";
                    }
                } else {
                    return "Неправильный формат команды. Используйте: update <id> <name> <coord_x> <coord_y> <from_x> <from_y> <to_x> <to_y> <distance>";
                }
            case "get":
                if (parts.length == 2) {
                    try {
                        long id = Long.parseLong(parts[1]);
                        Route route = collectionManager.getRouteById(id);
                        return route != null ? route.toString() : "Маршрут не найден";
                    } catch (NumberFormatException e) {
                        return "Неправильный формат числа";
                    }
                } else {
                    return "Неправильный формат команды. Используйте: get <id>";
                }
            case "exit":
                return "Соединение закрыто";
            default:
                return "Неизвестная команда";
        }
    }
}
