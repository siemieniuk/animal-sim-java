package siemieniuk.animals.math;

/**
 * This class represents two-coordinate point.
 * @author  Szymon Siemieniuk
 */
public class Coordinates {
    private static int MAX_DIM = 0;
    private int x;
    private int y;

    /**
     * A constructor with two integers
     * @param x First component
     * @param y Second component
     */
    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * A constructor which creates from int[] of length 2
     * @param components
     */
    public Coordinates(int[] components) {
        assert components.length == 2;
        this.x = components[0];
        this.y = components[1];
    }

    /**
     * Calculates the Manhattan distance to other point
     * @param other Other coordinates
     * @return The distance
     */
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

    public static void setMaxDimensions(int x, int y) {
        if (MAX_DIM == 0) {
            MAX_DIM = Math.max(x, y);
        } else {
            throw new IllegalArgumentException("MAX_DIM has currently been modified!");
        }
    }
}
