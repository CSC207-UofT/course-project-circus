package warehouse.storage;

import warehouse.storage.containers.InMemoryStorageUnitContainer;
import warehouse.storage.containers.StorageUnitContainer;
import warehouse.storage.strategies.MultiTypeStorageUnitStrategy;
import warehouse.storage.strategies.StorageUnitStrategy;

/**
 * Receives items from the outside world.
 */
public class ReceiveDepot extends StorageUnit {
    /**
     * Construct an in-memory ReceiveDepot with infinite capacity and a multi-type strategy.
     */
    public ReceiveDepot() {
        this(-1);
    }

    /**
     * Construct an in-memory ReceiveDepot with the given capacity and a multi-type strategy.
     */
    public ReceiveDepot(int capacity) {
        this(capacity, new MultiTypeStorageUnitStrategy(), new InMemoryStorageUnitContainer());
    }

    /**
     * Construct an in-memory ReceiveDepot with infinite capacity.
     */
    public ReceiveDepot(StorageUnitStrategy strategy) {
        this(-1, strategy, new InMemoryStorageUnitContainer());
    }

    /**
     * Construct an in-memory ReceiveDepot with the given capacity.
     */
    public ReceiveDepot(int capacity, StorageUnitStrategy strategy) {
        this(capacity, strategy, new InMemoryStorageUnitContainer());
    }

    /**
     * Construct a ReceiveDepot with the given strategy.
     *
     * @param capacity  The capacity of this StorageUnit.
     *                  If negative, then this StorageUnit has infinite capacity.
     * @param strategy  The strategy for this StorageUnit.
     * @param container The underlying data representation of this StorageUnit.
     */
    public ReceiveDepot(int capacity, StorageUnitStrategy strategy, StorageUnitContainer container) {
        super(capacity, strategy, container);
    }

    @Override
    public String toString() {
        return "ReceiveDepot{" +
                "capacity=" + getCapacity() +
                ", strategy=" + getStrategy() +
                ", container=" + getContainer() +
                '}';
    }
}
