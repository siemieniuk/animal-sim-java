package com.siemieniuk.animals;

public class Coordinates {
    private int x;
    private int y;
    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getManhattanDistanceTo(Coordinates other) {
        return Math.abs(x - other.getX()) + Math.abs(y - other.getY());
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
