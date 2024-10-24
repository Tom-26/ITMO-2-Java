package org.example;

import org.example.models.Coordinates;
import org.example.models.Location;
import org.example.models.Route;
import org.example.dao.RouteDao;

import java.sql.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.stream.IntStream;

public class CollectionManager {
    private LinkedList<Route> routes;
    private final Connection connection;
    private final String initializationDate; // Дата инициализации коллекции

    public CollectionManager(Connection connection) {
        this.connection = connection;
        this.initializationDate = DateTimeFormatter.ISO_ZONED_DATE_TIME.format(ZonedDateTime.now()); // Установка времени инициализации коллекции
        this.routes = new LinkedList<>();
        loadCollectionFromDatabase();
    }

    // Метод для загрузки коллекции из базы данных
    public void loadCollectionFromDatabase() {
        String query = "SELECT id, name, coordinates_x, coordinates_y, creation_date, location_from_x, location_from_y, location_from_name, location_to_x, location_to_y, location_to_name, distance, user_id FROM route";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            routes.clear();
            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                Coordinates coordinates = new Coordinates(rs.getFloat("coordinates_x"), rs.getLong("coordinates_y"));
                String creationDate = rs.getTimestamp("creation_date").toInstant().toString();
                Location from = new Location(rs.getFloat("location_from_x"), rs.getFloat("location_from_y"), rs.getString("location_from_name"));
                Location to = new Location(rs.getFloat("location_to_x"), rs.getFloat("location_to_y"), rs.getString("location_to_name"));
                long distance = rs.getLong("distance");
                int userId = rs.getInt("user_id");

                Route route = new Route(name, coordinates, creationDate, from, to, distance);
                route.setId(id);
                route.setUserId(userId);
                routes.add(route);
            }
            System.out.println("Коллекция успешно загружена из базы данных. Количество элементов: " + routes.size());
        } catch (SQLException e) {
            System.err.println("Ошибка при загрузке коллекции из базы данных: " + e.getMessage());
            this.routes = new LinkedList<>(); // Инициализация пустой коллекции на случай ошибки
        }
    }

    // Метод для сохранения коллекции в базу данных
    public void saveCollectionToDatabase() throws SQLException {
        connection.setAutoCommit(false); // Начинаем транзакцию

        try {
            // Очищаем текущие записи в таблице
            try (Statement clearStmt = connection.createStatement()) {
                clearStmt.executeUpdate("DELETE FROM route");
            }

            // Вставляем текущие маршруты
            String insertSQL = "INSERT INTO route (name, coordinates_x, coordinates_y, creation_date, location_from_x, location_from_y, location_from_name, location_to_x, location_to_y, location_to_name, distance, user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
                for (Route route : routes) {
                    pstmt.setString(1, route.getName());
                    pstmt.setFloat(2, route.getCoordinates().getX());
                    pstmt.setLong(3, route.getCoordinates().getY());
                    pstmt.setTimestamp(4, Timestamp.from(ZonedDateTime.parse(route.getCreationDate()).toInstant()));
                    pstmt.setFloat(5, route.getFrom().getX());
                    pstmt.setFloat(6, route.getFrom().getY());
                    pstmt.setString(7, route.getFrom().getName());
                    pstmt.setFloat(8, route.getTo().getX());
                    pstmt.setFloat(9, route.getTo().getY());
                    pstmt.setString(10, route.getTo().getName());
                    pstmt.setLong(11, route.getDistance());
                    pstmt.setInt(12, route.getUserId());
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
            }

            connection.commit(); // Завершаем транзакцию
            System.out.println("Коллекция успешно сохранена в базу данных.");
        } catch (SQLException e) {
            connection.rollback(); // Откатываем транзакцию в случае ошибки
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public LinkedList<Route> getRoutes() {
        return routes;
    }

    public String getInitializationDate() {
        return initializationDate;
    }

    // Метод для добавления нового маршрута
    public void addRoute(Route newRoute) {
        try {
            RouteDao routeDao = new RouteDao(connection);
            routeDao.addRoute(newRoute);
            loadCollectionFromDatabase(); // Обновляем коллекцию после добавления нового маршрута
            System.out.println("Маршрут добавлен и коллекция обновлена. Текущее количество маршрутов: " + routes.size());
        } catch (SQLException e) {
            System.err.println("Ошибка при добавлении маршрута: " + e.getMessage());
        }
    }

    // Метод для удаления маршрута по ID
    public boolean removeRouteById(long id) {
        boolean removed = routes.removeIf(route -> route.getId() == id);
        if (removed) {
            reindexRoutes();
            System.out.println("Маршрут с ID " + id + " был удален.");
        } else {
            System.out.println("Маршрут с ID " + id + " не найден.");
        }
        return removed;
    }

    // Метод для очистки всех маршрутов
    public void clearRoutes() {
        routes.clear();
        System.out.println("Все маршруты удалены.");
    }

    // Метод для удаления маршрута по индексу
    public void removeAt(int index) {
        if (index >= 0 && index < routes.size()) {
            routes.remove(index);
            reindexRoutes();
            System.out.println("Маршрут на позиции " + index + " был удален.");
        }
    }

    // Метод для удаления последнего маршрута
    public void removeLast() {
        if (!routes.isEmpty()) {
            routes.removeLast();
            reindexRoutes();
            System.out.println("Последний маршрут был удален.");
        }
    }

    // Метод для изменения порядка маршрутов на обратный
    public void reorder() {
        Collections.reverse(routes);
        reindexRoutes();
        System.out.println("Коллекция переупорядочена.");
    }

    // Метод для повторной индексации маршрутов
    private void reindexRoutes() {
        IntStream.range(0, routes.size()).forEach(i -> routes.get(i).setId((long) (i + 1)));
    }
}
