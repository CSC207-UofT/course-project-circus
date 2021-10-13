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
    public Rack() {
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
     * Return whether this Rack has infinite capacity.
     * @return True if the Rack has infinite capacity, or False otherwise.
     */
    public Boolean hasInfiniteCapacity() {
        return capacity < 0;
    }

    @Override
    public Boolean addItem(Item item) {
        if (currentItem == null) {
            currentItem = new Item(item);
        }

        if (item.equals(currentItem) && (hasInfiniteCapacity() || size < capacity)) {
            size += 1;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean removeItem(Item item) {
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

    @Override
    public Iterable<Item> getQueryItems() {
        if (currentItem == null) {
            return Collections.emptyList();
        } else {
            return List.of(currentItem);
        }
    }
}
