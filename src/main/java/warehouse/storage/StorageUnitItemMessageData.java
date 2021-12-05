package warehouse.storage;

import warehouse.inventory.Item;

/**
 * Data for onItemAdded/onItemRemoved messages on the StorageUnit.
 */
public class StorageUnitItemMessageData {
    private final Item item;
    private final StorageUnit storageUnit;

    /**
     * Construct StorageUnitItemMessageData given an Item and StorageUnit.
     *
     * @param item The Item that the operation was applied to.
     * @param storageUnit The StorageUnit that was modified.
     */
    public StorageUnitItemMessageData(Item item, StorageUnit storageUnit) {
        this.item = item;
        this.storageUnit = storageUnit;
    }

    public Item getItem() {
        return item;
    }

    public StorageUnit getStorageUnit() {
        return storageUnit;
    }
}