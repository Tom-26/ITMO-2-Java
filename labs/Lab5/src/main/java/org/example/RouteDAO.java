package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RouteDAO {

    // Метод для добавления нового маршрута в базу данных
    public boolean addRoute(Route route) {
        String query = "INSERT INTO route (name, coordinates_id, creation_date, from_location_id, to_location_id, distance, user_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, route.getName());
            statement.setInt(2, route.getCoordinates().getId());
            statement.setString(3, route.getCreationDate());
            statement.setInt(4, route.getFrom().getId());
            statement.setInt(5, route.getTo() != null ? route.getTo().getId() : null);
            statement.setLong(6, route.getDistance());
            statement.setLong(7, route.getUserId());

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Метод для получения маршрута по ID
    public Route getRouteById(long id) {
        String query = "SELECT * FROM route WHERE id = ?";

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Route route = new Route();
                route.setId(resultSet.getLong("id"));
                route.setName(resultSet.getString("name"));
                route.setCoordinates(getCoordinatesById(resultSet.getInt("coordinates_id")));
                route.setCreationDate(resultSet.getString("creation_date"));
                route.setFrom(getLocationById(resultSet.getInt("from_location_id")));
                route.setTo(getLocationById(resultSet.getInt("to_location_id")));
                route.setDistance(resultSet.getLong("distance"));
                route.setUserId(resultSet.getLong("user_id"));
                return route;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Метод для получения всех маршрутов
    public List<Route> getAllRoutes() {
        List<Route> routes = new ArrayList<>();
        String query = "SELECT * FROM route";

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Route route = new Route();
                route.setId(resultSet.getLong("id"));
                route.setName(resultSet.getString("name"));
                route.setCoordinates(getCoordinatesById(resultSet.getInt("coordinates_id")));
                route.setCreationDate(resultSet.getString("creation_date"));
                route.setFrom(getLocationById(resultSet.getInt("from_location_id")));
                route.setTo(getLocationById(resultSet.getInt("to_location_id")));
                route.setDistance(resultSet.getLong("distance"));
                route.setUserId(resultSet.getLong("user_id"));
                routes.add(route);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return routes;
    }

    // Метод для обновления маршрута
    public boolean updateRoute(long id, Route route) {
        String query = "UPDATE route SET name = ?, coordinates_id = ?, creation_date = ?, from_location_id = ?, to_location_id = ?, distance = ?, user_id = ? WHERE id = ?";

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, route.getName());
            statement.setInt(2, route.getCoordinates().getId());
            statement.setString(3, route.getCreationDate());
            statement.setInt(4, route.getFrom().getId());
            statement.setInt(5, route.getTo() != null ? route.getTo().getId() : null);
            statement.setLong(6, route.getDistance());
            statement.setLong(7, route.getUserId());
            statement.setLong(8, id);

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Метод для удаления маршрута
    public boolean deleteRoute(long id) {
        String query = "DELETE FROM route WHERE id = ?";

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, id);

            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Вспомогательные методы для получения координат и локаций по ID
    private Coordinates getCoordinatesById(int id) {
        String query = "SELECT * FROM coordinates WHERE id = ?";

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Coordinates coordinates = new Coordinates(resultSet.getFloat("x"), resultSet.getLong("y"));
                coordinates.setId(resultSet.getInt("id"));
                return coordinates;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Location getLocationById(int id) {
        String query = "SELECT * FROM location WHERE id = ?";

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Location location = new Location(resultSet.getFloat("x"), resultSet.getFloat("y"), resultSet.getString("name"));
                location.setId(resultSet.getInt("id"));
                return location;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
