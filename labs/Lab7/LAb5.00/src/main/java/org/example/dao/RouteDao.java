package org.example.dao;

import org.example.models.Route;
import org.example.models.Coordinates;
import org.example.models.Location;

import java.sql.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

public class RouteDao {
    private Connection connection;

    public RouteDao(Connection connection) {
        this.connection = connection;
    }

    public void addRoute(Route route) throws SQLException {
        String sql = "INSERT INTO route (name, coordinates_x, coordinates_y, creation_date, location_from_x, location_from_y, location_from_name, location_to_x, location_to_y, location_to_name, distance, user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, route.getName());
            statement.setFloat(2, route.getCoordinates().getX());
            statement.setLong(3, route.getCoordinates().getY());
            statement.setTimestamp(4, java.sql.Timestamp.from(ZonedDateTime.parse(route.getCreationDate(), DateTimeFormatter.ISO_ZONED_DATE_TIME).toInstant()));
            statement.setFloat(5, route.getFrom().getX());
            statement.setFloat(6, route.getFrom().getY());
            statement.setString(7, route.getFrom().getName());
            statement.setFloat(8, route.getTo().getX());
            statement.setFloat(9, route.getTo().getY());
            statement.setString(10, route.getTo().getName());
            statement.setLong(11, route.getDistance());
            statement.setInt(12, route.getUserId());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    route.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating route failed, no ID obtained.");
                }
            }
        }
    }


    public LinkedList<Route> getAllRoutes() throws SQLException {
        String sql = "SELECT * FROM route";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            LinkedList<Route> routes = new LinkedList<>();
            while (resultSet.next()) {
                Route route = new Route(
                        resultSet.getString("name"),
                        new Coordinates(resultSet.getFloat("coordinates_x"), resultSet.getLong("coordinates_y")),
                        resultSet.getTimestamp("creation_date").toInstant().toString(),
                        new Location(resultSet.getFloat("location_from_x"), resultSet.getFloat("location_from_y"), resultSet.getString("location_from_name")),
                        new Location(resultSet.getFloat("location_to_x"), resultSet.getFloat("location_to_y"), resultSet.getString("location_to_name")),
                        resultSet.getLong("distance")
                );
                route.setId(resultSet.getLong("id"));
                route.setUserId(resultSet.getInt("user_id"));
                routes.add(route);
            }
            return routes;
        }
    }
}
