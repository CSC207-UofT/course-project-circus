package warehouse;

import inventory.Item;

import java.util.Collections;
import java.util.List;

/**
 * A strategy that only allows StorageUnits to add items of a single type.
 *
 * More specifically, the SingleTypeStorageStrategy is homogenous with respect to the Items it stores. If the
 * StorageUnit is non-empty, then only an Item of the same type can be added to the StorageUnit.
 * Otherwise, if the StorageUnit is empty, any Item can be added.
 */
public class SingleTypeStorageStrategy implements StorageUnitStrategy {
    /**
     * Check whether an Item can be added into the given StorageUnit
     * @param storageUnit The StorageUnit to add to.
     * @param item The Item to check.
     * @return True if the Item type matches the rest of the items in the StorageUnit added, and False otherwise.
     */
    @Override
    public boolean canAddItem(StorageUnit storageUnit, Item item) {
        if (storageUnit.getSize() == 0) {
            return true;
        } else {
            Iterable<Item> items = storageUnit.getContainer().getItems();
            return item.equals(items.iterator().next());
        }
    }
}
