package circus.warehouse;

import circus.inventory.Item;

/**
 * A stateless 2D representation of the layout of a warehouse. A layout is a 2D grid of cells, where each cell can
 * contain a Unit such as a Rack or Depot.
 */
public class Warehouse {
    private final int width;
    private final int height;
    private final Tile[][] tiles;

    /**
     * Construct an empty layout with the given width and height.
     * @param width The width of the layout, in number of cells.
     * @param height The height of the layout, in number of cells.
     */
    public Warehouse(int width, int height) {
        this.width = width;
        this.height = height;
        // Initialise the cells
        this.tiles = new Tile[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tiles[x][y] = new Tile(x, y);
            }
        }
    }

    /**
     * Get the tile at the specified coordinate.
     * @param x The horizontal coordinate of the tile.
     * @param y The vertical coordinate of the tile.
     * @return The tile at the specified coordinate.
     * @throws TileOutOfBoundsException if the specified coordinates is out of bounds.
     */
    public Tile getTileAt(int x, int y) throws TileOutOfBoundsException {
        if (isTileCoordinateInRange(x, y)) {
            return tiles[x][y];
        } else {
            throw new TileOutOfBoundsException(x, y, width, height);
        }
    }

    /**
     * Find an empty Tile.
     * @return a Tile object with no StorageUnit attached to it, or null if no such Tile exists.
     */
    public Tile findEmptyTile() {
        for (int y = 0; y < height; y ++)
        {
            for (int x = 0; x < width; x++)
            {
                if (tiles[x][y].isEmpty()) {
                    return tiles[x][y];
                }
            }
        }
        return null;
    }

    /**
     * Find a Tile with an empty StorageUnit.
     * @return a Tile object with an empty StorageUnit attached to it, or null if no such Tile exists.
     */
//    public Tile findEmptyStorageUnitOfType(Class<? extends StorageUnit> type) {
//        for (int y = 0; y < height; y ++)
//        {
//            for (int x = 0; x < width; x++)
//            {
//                if (!tiles[x][y].isEmpty()) {
//                    StorageUnit storageUnit = tiles[x][y].getStorageUnit();
//                    if (storageUnit.getClass().isAssignableFrom(type) && storageUnit.getSize() == 0) {
//                        return tiles[x][y];
//                    }
//            }
//        }
//        return null;
//    }

        /**
         * Find an available Rack for the given Item.
         */
        public Tile findRackFor(Item item) {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    Tile tile = tiles[x][y];
                    if (tile.isEmpty() || !(tile.getStorageUnit() instanceof Rack rack)) continue;
                    if (rack.canAddItem(item)) {
                        return tile;
                    }
                }
            }
            return null;
        }

    /**
     * Check whether the given tile coordinate is out of bounds.
     * @param x The horizontal coordinate of the tile.
     * @param y The vertical coordinate of the tile.
     * @return True if the coordinate is within range (not out of bounds), and False otherwise.
     */
    public boolean isTileCoordinateInRange(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }
}
