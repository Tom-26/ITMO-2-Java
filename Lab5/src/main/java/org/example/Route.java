package org.example;
import java.time.ZonedDateTime;
import java.util.concurrent.atomic.AtomicLong;

public class Route implements Comparable<Route> {
    private static final AtomicLong ID_GENERATOR = new AtomicLong(0);

    public Route() {
        this.id = ID_GENERATOR.incrementAndGet();// логика генерации ID
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = java.time.ZonedDateTime.now(); // Текущее время и дата
        this.from = from;
        this.to = to;
        this.distance = distance;
    }

    private Long id; // Уникальный идентификатор
    private String name; // Имя маршрута
    private Coordinates coordinates; // Координаты
    private java.time.ZonedDateTime creationDate; // Дата создания
    private Location from; // Начальное местоположение
    private Location to; // Конечное местоположение
    private long distance; // Длина маршрута

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        if (id == null) {
            // exception null id
        } else {
            if (id <= 0) {
                // exception id<0
            } else {
            }
            this.id = id;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("'Name' не может быть null");
        } else {
            if (name.isEmpty()) {
                throw new IllegalArgumentException("'Name' не может быть пустым");
            } else {
                this.name = name;
            }
        }
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        if (coordinates == null) {
            throw new IllegalArgumentException("'Coordinates' не может быть null.");
        } else {
            this.coordinates = coordinates;
        }
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        if (creationDate == null) {
            throw new IllegalArgumentException("'Cretion Date' не может быть null.");
        } else {
            this.creationDate = creationDate;
        }
    }

    public Location getFrom() {
        return from;
    }

    public void setFrom(Location from) {
        if (from == null) {
            throw new IllegalArgumentException("'from' не может быть null.");
        } else {
            this.from = from;
        }
    }

    public Location getTo() {
        return to;
    }

    public void setTo(Location to) {
        if (to == null) {
            throw new IllegalArgumentException("'to' не может быть null.");
        } else {
            this.to = to;
        }
    }

    public long getDistance() {
        return distance;
    }

    public void setDistance(long distance) {
        if (distance <= 1) {
            throw new IllegalArgumentException("'Distance' не может быть меньше или равно 1");
        } else {
            this.distance = distance;
        }
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
                '}';
    }

    @Override
    public int compareTo(Route other) {
        return this.id.compareTo(other.id);
    }

    @Override
    public boolean equals(Object obj) {
        // Проверка на идентичность объектов
        if (this == obj) return true;

        // Проверка на null и сравнение классов объектов
        if (obj == null || getClass() != obj.getClass()) return false;

        // Приведение типа переданного объекта к типу Route
        Route route = (Route) obj;

        // Сравнение идентификаторов
        return java.util.Objects.equals(id, route.id);
    }
    @Override
    public int hashCode() {
        return java.util.Objects.hash(id);
    }
}