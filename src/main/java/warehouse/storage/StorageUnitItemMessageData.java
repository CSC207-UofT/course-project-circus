package warehouse.storage;

import inventory.Item;

/**
 * Data for onItemAdded/onItemRemoved messages on the StorageUnit.
 */
public record StorageUnitItemMessageData(Item item, StorageUnit storageUnit) {
    /**
     * Construct StorageUnitItemMessageData given an Item and StorageUnit.
     *
     * @param item The Item that the operation was applied to.
     * @param storageUnit The StorageUnit that was modified.
     */
    public StorageUnitItemMessageData {
    }
}