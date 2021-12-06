package warehouse;

import warehouse.robots.Robot;
import warehouse.tiles.EmptyTile;
import warehouse.tiles.EmptyTileChecker;
import warehouse.tiles.Tile;

import java.util.ArrayList;
import java.util.List;

/**
 * A 2D representation of a warehouse as a grid of Tiles.
 */
public class Warehouse implements EmptyTileChecker {
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
     * @return The tile at the specified coordinate, or null if the coordinates are invalid.
     */
    public Tile getTileAt(int x, int y) {
        if (isTileCoordinateInRange(x, y)) {
            return tiles[x][y];
        } else {
            return null;
        }
    }

    /**
     * Set a Tile in the Warehouse. The Warehouse tile corresponding to the position of the given Tile will be updated.
     * @param tile The new Tile.
     */
    public void setTile(Tile tile) {
        int x = tile.getX();
        int y = tile.getY();
        if (isTileCoordinateInRange(x, y)) {
            tiles[x][y] = tile;
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

    /**
     * Find all tiles of Type clazz in this Warehouse.
     * @param clazz The type of the Tile to find.
     * @return An Iterable of Tile objects.
     */
    public <T extends Tile> Iterable<T> findTilesOfType(Class<T> clazz) {
        List<T> tiles = new ArrayList<T>();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Tile tile = this.tiles[x][y];
                if (clazz.isInstance(tile)) {
                    tiles.add(clazz.cast(tile));
                }
            }
        }
        return tiles;
    }

    /**
     * Getter method for tiles
     * @return tiles
     */
    public Tile[][] getTiles() {
        return tiles;
    }

    /**
     * Return whether the given Tile is empty.
     * @param tile The Tile to check.
     * @return True if the given Tile is empty, and False otherwise.
     */
    @Override
    public boolean isEmpty(Tile tile) {
        return tile instanceof EmptyTile;
    }
}
