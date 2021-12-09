package warehouse.tiles;

/**
 * An empty Tile in the WarehouseLayout.
 */
public class EmptyTile extends Tile {
    /**
     * Construct an EmptyTile at the given index.
     *
     * @param index The index of the Tile.
     */
    public EmptyTile(int index) {
        super(index);
    }

    /**
     * Construct an unassociated EmptyTile, e.g. with an index of -1.
     */
    public EmptyTile() {
        super(-1);
    }

    @Override
    public String toString() {
        return "EmptyTile{" +
                "index=" + index +
                '}';
    }
}
