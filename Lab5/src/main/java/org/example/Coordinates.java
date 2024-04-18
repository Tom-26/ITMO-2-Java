package org.example;

public class Coordinates {
    private float x;
    private Long y; // Значение должно быть больше -122

    public Coordinates(float x, Long y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        if (x <= -236) {
            throw new IllegalArgumentException("'X' не может быть меньше -236.");
        } else {
            this.x = x;
        }
    }

    public Long getY() {
        return y;
    }

    public void setY(Long y) {
        if (y == null) {
            throw new IllegalArgumentException("'Y' не может быть null.");
        } else {
            if (y <= -122) {
                throw new IllegalArgumentException("'Y' не может быть меньше -122.");

            } else {
                this.y = y;
            }
        }
    }
    @Override
    public String toString() {
        return String.format("Coordinates{x=%.2f, y=%d}", x, y);
    }
}