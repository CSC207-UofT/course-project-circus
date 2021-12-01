package warehouse.logistics.assignment;

import warehouse.Warehouse;
import warehouse.inventory.Item;
import warehouse.tiles.StorageTile;

/**
 * A policy for assigning items to a StorageTile.
 */
public interface StorageTileAssignmentPolicy<T extends StorageTile> {
    /**
     * Assign the given item to a StorageTile in the given Warehouse.
     * @param item The incoming item.
     * @param warehouse The warehouse from which to choose a StorageTile.
     * @return the StorageTile assigned to the item, or null if the item could not be assigned a StorageTile.
     */
    T assign(Item item, Warehouse warehouse);
}
