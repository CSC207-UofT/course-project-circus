package warehouse.logistics.assignment;

import warehouse.WarehouseLayout;
import warehouse.inventory.Item;
import warehouse.tiles.StorageTile;

/**
 * A policy for assigning items to a StorageTile.
 */
public interface StorageTileAssignmentPolicy<T extends StorageTile> {
    /**
     * Assign the given item to a StorageTile in the given WarehouseLayout.
     * @param item The incoming item.
     * @return the StorageTile assigned to the item, or null if the item could not be assigned a StorageTile.
     */
    T assign(WarehouseLayout<?> layout, Item item);

    /**
     * Return whether the given Item can be assigned to a StorageTile in the given WarehouseLayout. Note that this does not
     * actually assign any StorageTile to this Item, and thus does not mutate any data.
     *
     * @remark By default, this method tries to assign the item to a StorageTile and returns True if the result is not
     * null. Note that if the policy mutates data on assign, then this method must be overridden to prevent side
     * effects.
     *
     * The separation between isAssignable and assign is useful when the assign method mutates the WarehouseLayout.
     * In which case, we might not want to actually mutate anything, but rather just determine whether an Item can
     * be assigned to a StorageTile.
     * @param item The item to check.
     * @return True if the item can be assigned to a StorageTile, and False otherwise.
     */
    default boolean isAssignable(WarehouseLayout<?> layout, Item item) {
        return assign(layout, item) != null;
    }
}
