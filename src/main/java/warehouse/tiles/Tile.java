package warehouse.tiles;

/**
 * A tile in the warehouse.
 */
public abstract class Tile {
    private final int x;
    private final int y;

    /**
     * Construct a Tile at the given position.
     * @param x The horizontal position of the tile.
     * @param y The vertical position of the tile.
     */
    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
