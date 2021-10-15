package circus.warehouse;
import circus.warehouse.Rack;
/**
 * An empty tile in the warehouse.
 */
public class Tile {
    private final int x;
    private final int y;
    private StorageUnit storageUnit;

    /**
     * Construct a WarehouseCell with the given position.
     * @param x The horizontal position of the cell.
     * @param y The vertical position of the cell.
     */
    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        storageUnit = null;
    }

    /**
     * Construct a WarehouseCell with the given position.
     * @param x The horizontal position of the cell.
     * @param y The vertical position of the cell.
     */
    public Tile(StorageUnit su, int x, int y) {
        this(x, y);
        storageUnit = su;
    }

    /**
     * Adds a StorageUnit into the tile.
     * @param s the storage unit being added.
     * @return True if the storageUnit was successfully added, otherwise false.
     */
    public boolean addStorageUnit(StorageUnit s)
    {
        if(storageUnit != null)
        {
            storageUnit = s;
            return true;
        }
        return false;
    }

    /**
     * Checks to see if the tile contains a storage unit.
     * @return True if the tile does not contain a storage unit, otherwise false.
     */
    public boolean isEmpty()
    {
        return storageUnit == null;
    }

    /**
     * Getter method for the storageUnit class variable.
     * @return the storageUnit
     */
    public StorageUnit getStorageUnit()
    {
        return storageUnit;
    }

}
