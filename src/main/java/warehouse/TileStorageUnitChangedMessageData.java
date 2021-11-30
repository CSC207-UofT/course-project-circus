package warehouse;

import warehouse.storage.StorageUnit;
import warehouse.tiles.Tile;

/**
 * Data for tile onStorageUnitChanged event.
 */
public class TileStorageUnitChangedMessageData {
    private final Tile tile;
    private final StorageUnit oldStorageUnit;

    /**
     * Construct a TileStorageUnitChangedMessageData given a Tile and StorageUnit.
     *
     * @param tile                The tile whose StorageUnit has changed.
     * @param oldStorageUnit The old value of the tile's storage unit.
     */
    public TileStorageUnitChangedMessageData(Tile tile, StorageUnit oldStorageUnit) {
        this.tile = tile;
        this.oldStorageUnit = oldStorageUnit;
    }

    public Tile getTile() {
        return tile;
    }

    public StorageUnit getOldStorageUnit() {
        return oldStorageUnit;
    }
}