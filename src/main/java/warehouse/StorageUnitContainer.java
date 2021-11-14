package warehouse;

import inventory.Item;

/**
 * The underlying container for StorageUnits.
 */
public interface StorageUnitContainer {
    /**
     * Store an Item in this data container.
     * @param item The Item to add.
     */
    void add(Item item);

    /**
     * Remove the given Item from this data counter.
     * @param item The item to remove.
     * @return True if the Item could be removed, False otherwise.
     */
    boolean remove(Item item);

    /**
     * Get the size (number of items stored) in this data container.
     * @return the number of items stored in this data container.
     */
    int getSize();

    /**
     * Get the contents of this data container.
     * @return An iterable of Item objects.
     */
    Iterable<Item> getItems();
}
