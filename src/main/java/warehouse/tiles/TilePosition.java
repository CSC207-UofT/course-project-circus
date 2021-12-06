package warehouse.tiles;

import java.util.Objects;

/**
 * The position of a Tile.
 */
public class TilePosition {
    private final int x;
    private final int y;

    /**
     * Construct a TilePosition at the given position.
     * @param x The horizontal position of the tile.
     * @param y The vertical position of the tile.
     */
    public TilePosition(int x, int y) {
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
        TilePosition that = (TilePosition) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "TilePosition{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
