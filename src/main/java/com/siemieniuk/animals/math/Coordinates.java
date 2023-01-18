package com.siemieniuk.animals.math;

public class Coordinates {
    private static int MAX_DIM = 0;
    private int x;
    private int y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinates(int[] coordinates) {
        if (coordinates.length == 2) {
            this.x = coordinates[0];
            this.y = coordinates[1];
        }
    }

    public int getManhattanDistanceTo(Coordinates other) {
        return Math.abs(x - other.getX()) + Math.abs(y - other.getY());
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Coordinates c)) {
            return false;
        }
        return (x == c.getX() && y == c.getY());
    }

    @Override
    public int hashCode() {
        return MAX_DIM*x+y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static void setMaxDim(int x, int y) {
        if (MAX_DIM == 0) {
            MAX_DIM = Math.max(x, y);
        } else {
            System.out.println("MAX_DIM has currently been modified!");
        }
    }
}
