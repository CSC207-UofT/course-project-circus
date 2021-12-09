package warehouse.tiles;

import messaging.Message;
import warehouse.inventory.Item;
import warehouse.storage.StorageUnit;
import warehouse.storage.containers.InMemoryStorageUnitContainer;
import warehouse.storage.strategies.MultiTypeStorageUnitStrategy;
import warehouse.transactions.ItemReceivedMessageData;
import warehouse.transactions.Receivable;

/**
 * Ships items from the warehouse to the outside world.
 */
public class ShipDepot extends StorageTile implements Receivable {
    private final Message<ItemReceivedMessageData> onItemReceivedMessage;

    /**
     * Construct an unassociated ShipDepot, e.g. with index -1, with an infinite capacity multi-type in-memory StorageUnit.
     * */
    public ShipDepot() {
        this(-1, -1);
    }

    /**
     * Construct a ShipDepot at the specified index with an infinite capacity multi-type in-memory StorageUnit.
     *
     * @param index The index of this ShipDepot.
     */
    public ShipDepot(int index) {
        this(index, -1);
    }

    /**
     * Construct a ShipDepot at the specified index with a multi-type in-memory StorageUnit with the given capacity.
     *
     * @param index The index of this ShipDepot. If negative, then this ShipDepot is unassociated.
     * @param capacity Maximum number of Items this ShipDepot can store.
     */
    public ShipDepot(int index, int capacity) {
        this(index, new StorageUnit(capacity, new MultiTypeStorageUnitStrategy(), new InMemoryStorageUnitContainer()));
    }

    /**
     * Construct a ShipDepot at the specified index with the given StorageUnit.
     *
     * @param index The index of this ShipDepot. If negative, then this ShipDepot is unassociated.
     * @param storageUnit The StorageUnit attached to this ShipDepot.
     */
    public ShipDepot(int index, StorageUnit storageUnit) {
        super(index, storageUnit);
        onItemReceivedMessage = new Message<>();
    }

    @Override
    public boolean receiveItem(Item item) {
        return storageUnit.addItem(item);
    }

    @Override
    public Tile getTile() {
        return this;
    }

    @Override
    public Message<ItemReceivedMessageData> getOnItemReceivedMessage() {
        return onItemReceivedMessage;
    }

    @Override
    public String toString() {
        return "ShipDepot{" +
                "storageUnit=" + storageUnit +
                ", index=" + index +
                '}';
    }
}
