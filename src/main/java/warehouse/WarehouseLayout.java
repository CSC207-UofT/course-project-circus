package warehouse;

import warehouse.geometry.WarehouseCoordinateSystem;
import warehouse.tiles.EmptyTile;
import warehouse.tiles.EmptyTileChecker;
import warehouse.tiles.Tile;
import warehouse.geometry.WarehouseCoordinate;

import java.util.ArrayList;
import java.util.List;

/**
 * A 2D representation of a warehouse as a grid of Tiles.
 */
public class WarehouseLayout<T extends WarehouseCoordinate> implements EmptyTileChecker {
    private final WarehouseCoordinateSystem<T> coordinateSystem;
    private final List<Tile> tiles;

    public WarehouseLayout(WarehouseCoordinateSystem<T> coordinateSystem) {
        this.coordinateSystem = coordinateSystem;
        int size = coordinateSystem.getSize();
        this.tiles = new ArrayList<>(size);
        // Initialises tiles
        for (int i = 0; i < size; i++) {
            tiles.add(new EmptyTile(i));
        }
    }

    /**
     * Get the tile at the specified coordinate.
     * @param position The position of the tile.
     * @return The tile at the specified coordinate, or null if the coordinates are invalid.
     */
    public Tile getTileAt(T position) {
        if (coordinateSystem.contains(position)) {
            return tiles.get(coordinateSystem.projectCoordinateToIndex(position));
        } else {
            return null;
        }
    }

    /**
     * Set a Tile in the WarehouseLayout.
     * @param position The position of the Tile to set.
     * @param tile The new Tile at the given position.
     */
    public void setTileAt(T position, Tile tile) {
        if (tile == null) return;
        if (coordinateSystem.contains(position)) {
            int index = coordinateSystem.projectCoordinateToIndex(position);
            setTileAt(index, tile);
        }
    }

    /**
     * Set a Tile in the WarehouseLayout.
     * @param index The index of the Tile to set.
     * @param tile The new Tile at the given position.
     */
    public void setTileAt(int index, Tile tile) {
        if (tile == null) return;
        if (index >= 0 && index <= tiles.size()) {
            tiles.set(index, tile);
            tile.setIndex(index);
        }
    }

    /**
     * Find all tiles of Type clazz in this WarehouseLayout.
     * @param clazz The type of the Tile to find.
     * @return A List of Tile objects.
     */
    public <U extends Tile> List<U> findTilesOfType(Class<U> clazz) {
        List<U> tiles = new ArrayList<>();
        for (Tile tile : getTiles()) {
            if (clazz.isInstance(tile)) {
                tiles.add(clazz.cast(tile));
            }
        }
        return tiles;
    }

    /**
     * Project this WarehouseLayout to a single dimension.
     * @return the Tiles of this Warehouse projected to a single dimension.
     */
    List<Tile> getTiles() {
        return new ArrayList<>(tiles);
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

    /**
     * Get the coordinate system.
     */
    public WarehouseCoordinateSystem<T> getCoordinateSystem() {
        return coordinateSystem;
    }
}
