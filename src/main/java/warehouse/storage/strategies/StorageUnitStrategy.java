package warehouse.storage.strategies;

import inventory.Item;
import warehouse.storage.StorageUnit;

/**
 * A strategy for storing items. This allows for constraints to be placed onto StorageUnits.
 */
public interface StorageUnitStrategy {
    /**
     * Indicates whether the given Item can be added.
     * @param item The Item to add.
     * @return True if the Item can be added, and False otherwise.
     */
    boolean canAddItem(StorageUnit storageUnit, Item item);
}
