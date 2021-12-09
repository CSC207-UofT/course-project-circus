package warehouse.tiles;

/**
 * A tile in the warehouse.
 */
public abstract class Tile {
    protected int index;

    /**
     * Construct a Tile with the given index.
     * @param index The index of the Tile.
     */
    public Tile(int index) {
        this.index = index;
    }

    /**
     * Construct an unassociated Tile, e.g. with index -1. See WarehouseCoordinateSystem javadocs for more details
     * about Tile association.
     */
    public Tile() {
        this(-1);
    }
    /**
     * Get the index of this Tile.
     * @return an integer.
     */
    public int getIndex() {
        return index;
    }

    /**
     * Set the index of this Tile.
     * @param index The new index of this Tile.
     */
    public void setIndex(int index) {
        this.index = index;
    }
}
