package warehouse.storage.strategies;

import warehouse.inventory.Item;
import warehouse.storage.StorageUnit;

/**
 * A strategy that only allows StorageUnits to add items of any type.
 */
public class MultiTypeStorageUnitStrategy implements StorageUnitStrategy {
    /**
     * Check whether an Item can be added into the given StorageUnit
     * @param storageUnit The StorageUnit to add to.
     * @param item The Item to check.
     * @return True always.
     */
    @Override
    public boolean canAddItem(StorageUnit storageUnit, Item item) {
        return true;
    }
}
