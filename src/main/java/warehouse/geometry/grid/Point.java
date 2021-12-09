package warehouse.geometry.grid;

import warehouse.geometry.WarehouseCoordinate;

import java.util.Objects;

/**
 * A 2-dimensional vector in N^2.
 */
public class Point implements WarehouseCoordinate {
    private final int x;
    private final int y;

    /**
     * Construct a Point at the given position.
     * @param x The horizontal position of the tile.
     * @param y The vertical position of the tile.
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point that = (Point) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return String.format("(%s, %s)", getX(), getY());
    }
}
