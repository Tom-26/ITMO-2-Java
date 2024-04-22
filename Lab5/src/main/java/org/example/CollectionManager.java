package org.example;
//я орёл
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.LinkedList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.stream.IntStream;

public class CollectionManager {
    private LinkedList<Route> routes;
    private final String filePath;
    private final String initializationDate; // Дата инициализации коллекции
    private static final Gson gson = new Gson();

    public CollectionManager(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new IllegalArgumentException("Путь к файлу не может быть пустым");
        }
        this.filePath = filePath;
        this.initializationDate = DateTimeFormatter.ISO_ZONED_DATE_TIME.format(ZonedDateTime.now()); // Установка времени инициализации коллекции
        this.routes = new LinkedList<>();
        loadCollection();
    }

    private void loadCollection() {
        try {
            if (Files.exists(Paths.get(filePath))) {
                String jsonContent = Files.readString(Paths.get(filePath));
                Type collectionType = new TypeToken<LinkedList<Route>>() {}.getType();
                this.routes = gson.fromJson(jsonContent, collectionType);
                System.out.println("Коллекция успешно загружена. Количество элементов: " + routes.size());
            } else {
                System.out.println("Файл не найден. Инициализируется пустая коллекция.");
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
            this.routes = new LinkedList<>(); // Инициализация пустой коллекции на случай ошибки
        }
    }

    public void saveCollection() throws IOException {
        String json = gson.toJson(routes);
        Files.writeString(Paths.get(filePath), json);
        System.out.println("");
    }

    public LinkedList<Route> getRoutes() {
        return routes;
    }

    public String getInitializationDate() {
        return DateTimeFormatter.ISO_ZONED_DATE_TIME.format(ZonedDateTime.now());
    }

    public void addRoute(Route newRoute) {
        this.routes.add(newRoute);
        System.out.println("Маршрут добавлен. Текущее количество маршрутов: " + routes.size());
    }

    public boolean removeRouteById(long id) {
        boolean removed = routes.removeIf(route -> route.getId() == id);
        if (removed) {
            System.out.println("Маршрут с ID " + id + " удален.");
        } else {
            System.out.println("Маршрут с ID " + id + " не найден.");
        }
        reindexRoutes();
        return removed;
    }

    public void clearRoutes() {
        routes.clear();
        System.out.println("Все маршруты удалены.");
    }

    public void removeAt(int index) {
        if (index >= 0 && index < routes.size()) {
            routes.remove(index);
            reindexRoutes();
            System.out.println("Маршрут на позиции " + index + " удален.");
        }
    }

    public void removeLast() {
        if (!routes.isEmpty()) {
            routes.removeLast();
            reindexRoutes();
            System.out.println("Последний маршрут удален.");
        }
    }

    public void reorder() {
        Collections.reverse(routes);
        System.out.println("Коллекция переупорядочена.");
    }
    private void reindexRoutes() {
        IntStream.range(0, routes.size()).forEach(i -> routes.get(i).setId((long) (i + 1)));
    }
}
