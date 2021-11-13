package warehouse;

import inventory.Item;

import java.util.Collections;
import java.util.List;

/**
 * A storage unit in the warehouse that can store items of a single type.
 *
 * More specifically, the SingleTypeStorageUnit is homogenous with respect to the Items it stores. If the
 * SingleTypeStorageUnit is non-empty, then only an Item of the same type can be added to the SingleTypeStorageUnit.
 * Otherwise, if the SingleTypeStorageUnit is empty, any Item can be added.
 */
public class SingleTypeStorageUnit extends StorageUnit {
    private final int capacity;
    /**
     * The current Item type stored in this SingleTypeStorageUnit.
     * If null, then no item is currently stored in this SingleTypeStorageUnit.
     */
    private Item currentItem;
    private int size;

    /**
     * Construct a SingleTypeStorageUnit with a capacity.
     * @param capacity The capacity of this SingleTypeStorageUnit.
     *                 If negative, then the SingleTypeStorageUnit has infinite capacity.
     */
    public SingleTypeStorageUnit(int capacity) {
        this.capacity = capacity;
        currentItem = null;
    }

    /**
     * Construct a SingleTypeStorageUnit with infinite capacity.
     */
    public SingleTypeStorageUnit() {
        this(-1);
    }

    /**
     * Get the capacity of this SingleTypeStorageUnit. If negative, then the capacity is infinite.
     * @return The capacity of this SingleTypeStorageUnit.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Get the size of this SingleTypeStorageUnit. In other words how many items are in this SingleTypeStorageUnit.
     * @return The size of this SingleTypeStorageUnit.
     */
    @Override
    public int getSize() {
        return size;
    }

    /**
     * Return whether this SingleTypeStorageUnit has infinite capacity.
     * @return True if the SingleTypeStorageUnit has infinite capacity, or False otherwise.
     */
    public boolean hasInfiniteCapacity() {
        return capacity < 0;
    }

    /**
     * Check whether an Item can be added into this SingleTypeStorageUnit.
     * @param item The Item to check.
     * @return True if the Item can be added, and False otherwise.
     */
    public boolean canAddItem(Item item) {
        return currentItem == null || (item.equals(currentItem) && (hasInfiniteCapacity() || size < capacity));
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

    @Override
    public String toString() {
        return "SingleTypeStorageUnit{" +
                "capacity=" + capacity +
                ", currentItem=" + currentItem +
                ", size=" + size +
                '}';
    }
}
