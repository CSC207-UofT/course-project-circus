package warehouse;

import inventory.Item;

import java.util.HashMap;
import java.util.Map;

/**
 * A strategy that only allows StorageUnits to add items of any type.
 */
public class MultiTypeStorageUnit implements StorageUnitStrategy {
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
