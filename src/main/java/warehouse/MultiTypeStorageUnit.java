package warehouse;

import inventory.Item;

import java.util.HashMap;
import java.util.Map;

/**
 * A storage unit in the warehouse that can store items of any type.
 */
public class MultiTypeStorageUnit extends StorageUnit {
    /**
     * A mapping from Items to the quantity stored in this MultiTypeStorageUnit.
     */
    private final Map<Item, Integer> items;
    private final int capacity;

    /**
     * Construct a MultiTypeStorageUnit with a capacity.
     * @param maxCapacity The maximum capacity of this MultiTypeStorageUnit. If negative, then the capacity is infinite.
     */
    public MultiTypeStorageUnit(int maxCapacity) {
        this.items = new HashMap<>();
        this.capacity = maxCapacity;
    }

    /**
     * Construct a MultiTypeStorageUnit with infinite capacity.
     */
    public MultiTypeStorageUnit() {
        this(-1);
    }


    /**
     * Add an item to this MultiTypeStorageUnit.
     * @param item The item to add.
     * @return True if the Item could be added, False otherwise.
     */
    public boolean addItem(Item item) {
        if (hasInfiniteCapacity() || getSize() < capacity) {
            int newQuantity = items.getOrDefault(item, 0) + 1;
            items.put(item, newQuantity);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Remove the given Item from this MultiTypeStorageUnit.
     * @param item The item to remove.
     * @return True if the Item could be removed, False otherwise.
     */
    public boolean removeItem(Item item) {
        if (items.containsKey(item) && items.get(item) > 0) {
            int newQuantity = items.get(item) - 1;
            items.put(item, newQuantity);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Get the capacity of this MultiTypeStorageUnit. If negative, then the capacity is infinite.
     * @return The capacity of this MultiTypeStorageUnit.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Return whether this MultiTypeStorageUnit has infinite capacity.
     * @return True if the MultiTypeStorageUnit has infinite capacity, or False otherwise.
     */
    public boolean hasInfiniteCapacity() {
        return capacity < 0;
    }

    /**
     * Get the size of this MultiTypeStorageUnit, which is the sum of quantities for each Item type.
     * @return The size of this MultiTypeStorageUnit.
     */
    @Override
    public int getSize() {
        int size = 0;
        for (int quantity : items.values()) {
            size += quantity;
        }
        return size;
    }

    @Override
    public Iterable<Item> getQueryItems() {
        return items.keySet();
    }

    @Override
    public String toString() {
        return "MultiTypeStorageUnit{" +
                "items=" + items +
                ", capacity=" + capacity +
                '}';
    }
}
