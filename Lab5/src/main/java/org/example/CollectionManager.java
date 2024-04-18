package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.LinkedList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class CollectionManager {
    private LinkedList<Route> routes;
    private String filePath;
    private ZonedDateTime initializationDate;
    private static final Gson gson = new Gson();

    // Конструктор для инициализации через файл
    public CollectionManager(String filePath) {
        this.filePath = filePath;
        this.routes = new LinkedList<>();
        this.initializationDate = ZonedDateTime.now();
        loadCollection();
    }

    // Конструктор для прямой инициализации списка маршрутов
    public CollectionManager(LinkedList<Route> routes) {
        this.routes = routes;
        this.initializationDate = ZonedDateTime.now();
    }

    private void loadCollection() {
        try {
            String jsonContent = new String(Files.readAllBytes(Paths.get(filePath)));
            Type collectionType = new TypeToken<LinkedList<Route>>() {}.getType();
            this.routes = gson.fromJson(jsonContent, collectionType);
            System.out.println("Коллекция успешно загружена. Количество элементов: " + routes.size());
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }

    public void saveCollection() throws IOException {
        String json = gson.toJson(routes);
        Files.write(Paths.get(filePath), json.getBytes());
        System.out.println("Коллекция успешно сохранена.");
    }

    public LinkedList<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(LinkedList<Route> routes) {
        this.routes = routes;
    }

    public ZonedDateTime getInitializationDate() {
        return initializationDate;
    }

    public void addRoute(Route route) {
        routes.add(route);
        System.out.println("Маршрут добавлен. Текущее количество маршрутов: " + routes.size());
    }

    public boolean removeRouteById(long id) {
        boolean removed = routes.removeIf(route -> route.getId().equals(id));
        if (removed) {
            System.out.println("Маршрут с ID " + id + " удален.");
        } else {
            System.out.println("Маршрут с ID " + id + " не найден.");
        }
        return removed;
    }

    public void clearRoutes() {
        routes.clear();
        System.out.println("Все маршруты удалены.");
    }

    public void removeAt(int index) {
        if (index >= 0 && index < routes.size()) {
            routes.remove(index);
            System.out.println("Маршрут на позиции " + index + " удален.");
        }
    }

    public void removeLast() {
        if (!routes.isEmpty()) {
            routes.removeLast();
            System.out.println("Последний маршрут удален.");
        }
    }

    public void reorder() {
        Collections.reverse(routes);
        System.out.println("Коллекция переупорядочена.");
    }
}
