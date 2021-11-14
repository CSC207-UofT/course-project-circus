package warehouse;

import messaging.Message;
import warehouse.storage.StorageUnit;

/**
 * An empty tile in the warehouse.
 */
public class Tile {
    private final int x;
    private final int y;
    private StorageUnit storageUnit;

    private final Message<TileStorageUnitChangedMessageData> onStorageUnitChangedMessage;

    /**
     * Construct a WarehouseCell with the given position and StorageUnit.
     * @param x The horizontal position of the cell.
     * @param y The vertical position of the cell.
     * @param storageUnit Storage unit attached to this tile.
     */
    public Tile(int x, int y, StorageUnit storageUnit) {
        this.x = x;
        this.y = y;
        this.storageUnit = storageUnit;
        onStorageUnitChangedMessage = new Message<>();
    }

    /**
     * Construct a WarehouseCell with the given position.
     * @param x The horizontal position of the cell.
     * @param y The vertical position of the cell.
     */
    public Tile(int x, int y) {
        this(x,y, null);
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

    /**
     * Set the StorageUnit on this Tile. If a StorageUnit is already on this Tile, it will be OVERWRITTEN!
     * @param storageUnit the storage unit being added.
     */
    public void setStorageUnit(StorageUnit storageUnit) {
        if (this.storageUnit != null && this.storageUnit.equals(storageUnit)) return;
        StorageUnit oldStorageUnit = this.storageUnit;
        this.storageUnit = storageUnit;
        onStorageUnitChangedMessage.execute(new TileStorageUnitChangedMessageData(
                this, oldStorageUnit
        ));
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Message<TileStorageUnitChangedMessageData> getOnStorageUnitChangedMessage() {
        return onStorageUnitChangedMessage;
    }
}
