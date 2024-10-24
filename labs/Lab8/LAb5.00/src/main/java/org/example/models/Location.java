package org.example.models;

public class Location {
    private Float x;
    private Float y;
    private String name;

    public Location(Float x, Float y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public Float getX() {
        return x;
    }

    public void setX(Float x) {
        this.x = x;
    }

    public Float getY() {
        return y;
    }

    public void setY(Float y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Location fromString(String locationStr) {
        // Assuming the locationStr is in the format "x,y,name"
        String[] parts = locationStr.split(",");
        Float x = Float.parseFloat(parts[0]);
        Float y = Float.parseFloat(parts[1]);
        String name = parts[2];
        return new Location(x, y, name);
    }

    @Override
    public String toString() {
        return x + "," + y + "," + name;
    }
}
