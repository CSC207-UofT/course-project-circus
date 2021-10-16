package main.java.warehouse;

import main.java.inventory.Item;
import main.java.query.Query;
import main.java.Distributable;
import main.java.Receivable;

import java.util.List;

/**
 * A unit in the warehouse that can store items.
 */
public abstract class StorageUnit implements Receivable, Distributable {
    /**
     * Add an item to this StorageUnit.
     * @param item The item to add.
     * @return True if the Item could be added, False otherwise.
     */
    public abstract boolean addItem (Item item);
    /**
     * Remove the given Item from this StorageUnit.
     * @param item The item to remove.
     * @return True if the Item could be removed, False otherwise.
     */
    public abstract boolean removeItem(Item item);

    /**
     * Get the size (number of items stored) of this StorageUnit.
     * @return the number of items stored in this StorageUnit.
     */
    public abstract int getSize();

    @Override
    public boolean receiveItem(Item item) {
        return addItem(item);
    }

    @Override
    public Item distributeItem(Query<Item> itemQuery) {
        List<Item> queryResult = query(itemQuery);
        if (queryResult.size() == 0) {
            // Throw exception
            return null;
        }
        // Try removing the item
        Item item = queryResult.get(0);
        if (!removeItem(item)) {
            return null;
        }
        // Copy before returning
        return new Item(item);
    }
}
