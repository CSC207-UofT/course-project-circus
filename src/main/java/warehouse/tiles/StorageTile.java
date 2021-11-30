package warehouse.tiles;

import warehouse.storage.StorageUnit;

/**
 * A Tile that has a StorageUnit on it.
 */
public abstract class StorageTile extends Tile {
    protected StorageUnit storageUnit;

    /**
     * Construct a StorageTile at the specified position with the given StorageUnit.
     * @param x The horizontal coordinate of this StorageTile.
     * @param y The vertical coordinate of this StorageTile.
     * @param storageUnit The StorageUnit attached to this StorageTile. This cannot be null.
     */
    public StorageTile(int x, int y, StorageUnit storageUnit) {
        super(x, y);
        setStorageUnit(storageUnit);
    }

    public StorageUnit getStorageUnit() {
        return storageUnit;
    }

    /**
     * Set the StorageUnit of this StorageTile. This cannot be null.
     * @param storageUnit The new StorageUnit.
     */
    public void setStorageUnit(StorageUnit storageUnit) {
        assert storageUnit != null;
        this.storageUnit = storageUnit;
    }
}
