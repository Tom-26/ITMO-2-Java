package org.example;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

public class Route implements Comparable<Route> {
    private static final AtomicLong ID_GENERATOR = new AtomicLong(0);

    // Конструктор без параметров
    public Route() {
    }

    public Route(String name, Coordinates coordinates, String creationDate, Location from, Location to, long distance) {
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate != null ? creationDate : DateTimeFormatter.ISO_ZONED_DATE_TIME.format(ZonedDateTime.now());
        this.from = from;
        this.to = to;
        this.distance = distance;
        this.id = ID_GENERATOR.incrementAndGet();
    }

    private Long id; // Уникальный идентификатор
    private String name; // Имя маршрута
    private Coordinates coordinates; // Координаты
    private String creationDate; // Дата создания
    private Location from; // Начальное местоположение
    private Location to; // Конечное местоположение
    private long distance; // Длина маршрута
    private long index; // Порядковый номер
    private long userId; // Идентификатор пользователя

    // Геттеры и сеттеры

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID не может быть null");
        }
        if (id <= 0) {
            throw new IllegalArgumentException("ID не может быть меньше или равно 0");
        }
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("'Name' не может быть null");
        }
        if (name.isEmpty()) {
            throw new IllegalArgumentException("'Name' не может быть пустым");
        }
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        if (coordinates == null) {
            throw new IllegalArgumentException("'Coordinates' не может быть null.");
        }
        this.coordinates = coordinates;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        if (creationDate == null) {
            throw new IllegalArgumentException("'Creation Date' не может быть null.");
        }
        this.creationDate = creationDate.format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
    }

    public Location getFrom() {
        return from;
    }

    public void setFrom(Location from) {
        if (from == null) {
            throw new IllegalArgumentException("'from' не может быть null.");
        }
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
        if (distance <= 1) {
            throw new IllegalArgumentException("'Distance' не может быть меньше или равно 1");
        }
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Route{" + "index= " + index +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", from=" + from +
                ", to=" + to +
                ", distance=" + distance +
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
        return java.util.Objects.equals(id, route.id);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id);
    }
}
