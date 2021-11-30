package warehouse;

import warehouse.inventory.Item;
import warehouse.storage.Rack;
import warehouse.storage.StorageUnit;
import warehouse.tiles.EmptyTile;
import warehouse.tiles.Tile;

/**
 * A 2D representation of a warehouse as a grid of Tiles.
 */
public class Warehouse {
    private final int width;
    private final int height;
    private final Tile[][] tiles;

    /**
     * Construct an empty layout with the given width and height.
     * @param width  The width of the layout, in number of cells.
     * @param height The height of the layout, in number of cells.
     */
    public Warehouse(int width, int height) {
        this.width = width;
        this.height = height;
        this.tiles = new Tile[width][height];
        // Initialise tiles
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tiles[x][y] = new EmptyTile(x, y);
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
     * Set a Tile in the Warehouse. The Warehouse tile corresponding to the position of the given Tile will be updated.
     * @param tile The new Tile.
     * @throws TileOutOfBoundsException if the position of the given Tile is out of bounds.
     */
    public void setTile(Tile tile) throws TileOutOfBoundsException {
        int x = tile.getX();
        int y = tile.getY();
        if (isTileCoordinateInRange(x, y)) {
            tiles[x][y] = tile;
        } else {
            throw new TileOutOfBoundsException(x, y, width, height);
        }
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

    /**
     * Get the width of this Warehouse.
     * @return an integer specifying the width of this Warehouse, in number of tiles.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get the height of this Warehouse.
     * @return an integer specifying the height of this Warehouse, in number of tiles.
     */
    public int getHeight() {
        return height;
    }
}
