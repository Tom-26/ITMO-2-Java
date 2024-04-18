package org.example;

public class
Location {
    private Float x;
    private Float y;
    private String name; // Может быть null
    public Location(Float x, Float y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }
    public Float getX() {
        return x;
    }

    public void setX(Float x) {
        if(x == null){
            throw new IllegalArgumentException("'X' не может быть null.");
        } else{
        this.x = x;
        }
    }

    public Float getY() {
        return y;
    }

    public void setY(Float y) {
        if (y == null){
            throw new IllegalArgumentException("'Y' не может быть null.");
        }
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        if (name != null) {
            return String.format("Location{x=%.2f, y=%.2f, name=%s}", x, y, name);
        } else {
            return String.format("Location{x=%.2f, y=%.2f}", x, y);
        }
    }
}
