package warehouse;

import inventory.Item;
import query.Query;

import java.util.List;

/**
 * A container that can store items.
 */
public class StorageUnit implements Receivable, Distributable {
    private final int capacity;
    private final StorageUnitStrategy strategy;
    private final StorageUnitContainer container;

    /**
     * Construct a StorageUnit with the given strategy.
     * @param capacity The capacity of this StorageUnit.
     *                 If negative, then this StorageUnit has infinite capacity.
     * @param strategy The strategy for this StorageUnit.
     * @param container The underlying data representation of this StorageUnit.
     */
    public StorageUnit(int capacity, StorageUnitStrategy strategy, StorageUnitContainer container) {
        this.capacity = capacity;
        this.strategy = strategy;
        this.container = container;
    }

    /**
     * Add an item to this StorageUnit.
     * @param item The Item to add.
     * @return True if the Item could be added, False otherwise.
     */
    public boolean addItem(Item item) {
        if (canAddItem(item)) {
            container.add(item);
            return true;
        }
        return false;
    }

    /**
     * Remove the given Item from this StorageUnit.
     * @param item The item to remove.
     * @return True if the Item could be removed, False otherwise.
     */
    public boolean removeItem(Item item) {
        return container.remove(item);
    }

    /**
     * Get the capacity of this SingleTypeStorageStrategy. If negative, then the capacity is infinite.
     * @return The capacity of this SingleTypeStorageStrategy.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Get the size (number of items stored) of this StorageUnit.
     * @return the number of items stored in this StorageUnit.
     */
    public int getSize() {
        return container.getSize();
    }

    /**
     * Return whether this SingleTypeStorageStrategy has infinite capacity.
     * @return True if the SingleTypeStorageStrategy has infinite capacity, or False otherwise.
     */
    public boolean hasInfiniteCapacity() {
        return capacity < 0;
    }

    /**
     * Check whether an Item can be added into this Rack.
     * @param item The Item to check.
     * @return True if the Item can be added, and False otherwise.
     */
    public boolean canAddItem(Item item) {
        return (hasInfiniteCapacity() || getSize() < capacity) && strategy.canAddItem(this, item);
    }

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

    @Override
    public Iterable<Item> getQueryItems() {
        return container.getItems();
    }

    public StorageUnitStrategy getStrategy() {
        return strategy;
    }

    public StorageUnitContainer getContainer() {
        return container;
    }
}
