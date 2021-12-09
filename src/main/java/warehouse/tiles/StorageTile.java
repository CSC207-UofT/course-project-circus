package warehouse.tiles;

import warehouse.storage.StorageUnit;

/**
 * A Tile that has a StorageUnit on it.
 */
public abstract class StorageTile extends Tile {
    protected StorageUnit storageUnit;

    /**
     * Construct a StorageTile at the specified index with the given StorageUnit.
     * @param index The index of the tile.
     * @param storageUnit The StorageUnit attached to this StorageTile. This cannot be null.
     */
    public StorageTile(int index, StorageUnit storageUnit) {
        super(index);
        setStorageUnit(storageUnit);
    }

    /**
     * Construct an unassociated StorageTile, e.g. with index -1, with the given StorageUnit.
     * @param storageUnit The StorageUnit attached to this StorageTile. This cannot be null.
     */
    public StorageTile(StorageUnit storageUnit) {
        super(-1);
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
