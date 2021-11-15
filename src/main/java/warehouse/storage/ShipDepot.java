package warehouse.storage;

import warehouse.storage.containers.InMemoryStorageUnitContainer;
import warehouse.storage.containers.StorageUnitContainer;
import warehouse.storage.strategies.MultiTypeStorageUnitStrategy;
import warehouse.storage.strategies.StorageUnitStrategy;

/**
 * Ships Items to the outside world.
 */
public class ShipDepot extends StorageUnit {
    /**
     * Construct an in-memory ShipDepot with infinite capacity and a multi-type strategy.
     */
    public ShipDepot() {
        this(-1);
    }

    /**
     * Construct an in-memory ShipDepot with the given capacity and a multi-type strategy.
     */
    public ShipDepot(int capacity) {
        this(capacity, new MultiTypeStorageUnitStrategy(), new InMemoryStorageUnitContainer());
    }

    /**
     * Construct an in-memory ShipDepot with infinite capacity.
     */
    public ShipDepot(StorageUnitStrategy strategy) {
        this(-1, strategy, new InMemoryStorageUnitContainer());
    }

    /**
     * Construct an in-memory ShipDepot with the given capacity.
     */
    public ShipDepot(int capacity, StorageUnitStrategy strategy) {
        this(capacity, strategy, new InMemoryStorageUnitContainer());
    }

    /**
     * Construct a ShipDepot with the given strategy.
     *
     * @param capacity  The capacity of this StorageUnit.
     *                  If negative, then this StorageUnit has infinite capacity.
     * @param strategy  The strategy for this StorageUnit.
     * @param container The underlying data representation of this StorageUnit.
     */
    public ShipDepot(int capacity, StorageUnitStrategy strategy, StorageUnitContainer container) {
        super(capacity, strategy, container);
    }
}
