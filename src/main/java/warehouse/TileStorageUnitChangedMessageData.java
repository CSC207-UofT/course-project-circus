package warehouse;

import warehouse.Tile;
import warehouse.storage.StorageUnit;

/**
 * Data for tile onStorageUnitChanged event.
 */
public record TileStorageUnitChangedMessageData(Tile tile, StorageUnit oldStorageUnit) {
    /**
     * Construct a TileStorageUnitChangedMessageData given a Tile and StorageUnit.
     *
     * @param tile                The tile whose StorageUnit has changed.
     * @param oldStorageUnit The old value of the tile's storage unit.
     */
    public TileStorageUnitChangedMessageData {
    }
}