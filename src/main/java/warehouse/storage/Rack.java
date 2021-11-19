package warehouse.storage;

import warehouse.storage.containers.InMemoryStorageUnitContainer;
import warehouse.storage.containers.StorageUnitContainer;
import warehouse.storage.strategies.SingleTypeStorageStrategy;
import warehouse.storage.strategies.StorageUnitStrategy;

/**
 * Stores Items in the Warehouse.
 */
public class Rack extends StorageUnit {
    /**
     * Construct an in-memory Rack with infinite capacity and a single-type strategy.
     */
    public Rack() {
        this(-1);
    }

    /**
     * Construct an in-memory Rack with the given capacity and a single-type strategy.
     */
    public Rack(int capacity) {
        this(capacity, new SingleTypeStorageStrategy(), new InMemoryStorageUnitContainer());
    }

    /**
     * Construct an in-memory Rack with infinite capacity.
     */
    public Rack(StorageUnitStrategy strategy) {
        this(-1, strategy, new InMemoryStorageUnitContainer());
    }

    /**
     * Construct an in-memory Rack with the given capacity.
     */
    public Rack(int capacity, StorageUnitStrategy strategy) {
        this(capacity, strategy, new InMemoryStorageUnitContainer());
    }

    /**
     * Construct a Rack with the given strategy.
     *
     * @param capacity  The capacity of this StorageUnit.
     *                  If negative, then this StorageUnit has infinite capacity.
     * @param strategy  The strategy for this StorageUnit.
     * @param container The underlying data representation of this StorageUnit.
     */
    public Rack(int capacity, StorageUnitStrategy strategy, StorageUnitContainer container) {
        super(capacity, strategy, container);
    }

    @Override
    public String toString() {
        return "Rack{" +
                "capacity=" + getCapacity() +
                ", strategy=" + getStrategy() +
                ", container=" + getContainer() +
                '}';
    }
}
