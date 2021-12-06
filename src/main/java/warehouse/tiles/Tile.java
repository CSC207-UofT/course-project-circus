package warehouse.tiles;

/**
 * A tile in the warehouse.
 */
public abstract class Tile {
    private final TilePosition position;

    /**
     * Construct a Tile with the given position.
     * @param position The position of the Tile.
     */
    public Tile(TilePosition position) {
        this.position = position;
    }

    /**
     * Construct a Tile at the given position.
     * @param x The horizontal position of the tile.
     * @param y The vertical position of the tile.
     */
    public Tile(int x, int y) {
        this(new TilePosition(x, y));
    }

    /**
     * Get the position of this Tile.
     * @return a TilePosition object.
     */
    public TilePosition getPosition() {
        return position;
    }

    /**
     * Get the horizontal coordinate of this Tile.
     */
    public int getX() {
        return position.getX();
    }

    /**
     * Get the vertical coordinate of this Tile.
     */
    public int getY() {
        return position.getY();
    }
}
