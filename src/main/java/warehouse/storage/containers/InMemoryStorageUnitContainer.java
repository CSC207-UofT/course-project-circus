package warehouse.storage.containers;

import inventory.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * A StorageUnit that stores Items in-memory (via an ArrayList).
 */
public class InMemoryStorageUnitContainer implements StorageUnitContainer {
    private final List<Item> items;

    /**
     * Construct a new InMemoryStorageUnitContainer.
     */
    public InMemoryStorageUnitContainer() {
        items = new ArrayList<>();
    }

    @Override
    public void add(Item item) {
        items.add(item);
    }

    /**
     * Remove the given Item from this data counter.
     * @param item The item to remove.
     * @return True if the Item could be removed, False otherwise.
     */
    public boolean remove(Item item) {
        return items.remove(item);
    }

    /**
     * Get the size (number of items stored) in this data container.
     * @return the number of items stored in this data container.
     */
    public int getSize() {
        return items.size();
    }

    @Override
    public Iterable<Item> getItems() {
        return items;
    }
}
