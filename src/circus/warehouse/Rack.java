package circus.warehouse;

import circus.inventory.Item;

import java.util.Collections;
import java.util.List;

/**
 * A storage unit in the warehouse that can store items of a single type.
 *
 * More specifically, the Rack is homogenous with respect to the Items it stores. If the Rack is non-empty, then only
 * an Item of the same type can be added to the Rack. Otherwise, if the Rack is empty, any Item can be added.
 */
public class Rack extends StorageUnit {
    private final int capacity;
    /**
     * The current Item type stored in this Rack. If null, then no item is currently stored in this Rack.
     */
    private Item currentItem;
    private int size;

    /**
     * Construct a Rack with a capacity.
     * @param capacity The capacity of this Rack. If negative, then the Rack has infinite capacity.
     */
    public Rack(int capacity) {
        this.capacity = capacity;
        currentItem = null;
    }

    /**
     * Construct a Rack with infinite capacity.
     */
    public Rack(int x, int y) {
        this(-1);
    }

    /**
     * Get the capacity of this Rack. If negative, then the capacity is infinite.
     * @return The capacity of this Rack.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Get the size of this Rack. In other words how many items are in this rack.
     * @return The size of this Rack.
     */
    @Override
    public int getSize() {
        return size;
    }

    /**
     * Return whether this Rack has infinite capacity.
     * @return True if the Rack has infinite capacity, or False otherwise.
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
        return item.equals(currentItem) && (hasInfiniteCapacity() || size < capacity);
    }

    @Override
    public boolean addItem(Item item) {
        if (currentItem == null) {
            currentItem = new Item(item);
        }

        if (canAddItem(item)) {
            size += 1;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean removeItem(Item item) {
        if (item.equals(currentItem) && size > 0) {
            size -= 1;
            if (size == 0) {
                currentItem = null;
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns a List of one element, which is currentItem, if currentItem exists, otherwise, an empty list.
     * @return a List of Query Items
     */
    @Override
    public List<Item> getQueryItems() {
        if (currentItem == null) {
            return Collections.emptyList();
        } else {
            return List.of(currentItem);
        }
    }
}
