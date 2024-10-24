package org.example.models;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Route implements Comparable<Route> {
    private Long id;
    private String name;
    private Coordinates coordinates;
    private String creationDate;
    private Location from;
    private Location to;
    private long distance;
    private int userId; // Новый параметр для хранения идентификатора пользователя

    public Route(String name, Coordinates coordinates, Location from, Location to, long distance) {
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = DateTimeFormatter.ISO_ZONED_DATE_TIME.format(ZonedDateTime.now());
        this.from = from;
        this.to = to;
        this.distance = distance;
    }

    public Route(String name, Coordinates coordinates, String creationDate, Location from, Location to, long distance) {
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.from = from;
        this.to = to;
        this.distance = distance;
    }

    // Getter и Setter методы

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public Location getFrom() {
        return from;
    }

    public void setFrom(Location from) {
        this.from = from;
    }

    public Location getTo() {
        return to;
    }

    public void setTo(Location to) {
        this.to = to;
    }

    public long getDistance() {
        return distance;
    }

    public void setDistance(long distance) {
        this.distance = distance;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", from=" + from +
                ", to=" + to +
                ", distance=" + distance +
                ", userId=" + userId +
                '}';
    }

    @Override
    public int compareTo(Route other) {
        return this.id.compareTo(other.id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Route route = (Route) obj;
        return id.equals(route.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
