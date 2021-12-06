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
     * Construct a ShipDepot at the specified position with an infinite capacity multi-type in-memory StorageUnit.
     *
     * @param x The horizontal coordinate of this ShipDepot.
     * @param y The vertical coordinate of this ShipDepot.
     */
    public ShipDepot(int x, int y) {
        this(x, y, -1);
    }

    /**
     * Construct a ShipDepot at the specified position with a multi-type in-memory StorageUnit with the given capacity.
     *
     * @param x The horizontal coordinate of this ShipDepot.
     * @param y The vertical coordinate of this ShipDepot.
     * @param capacity Maximum number of Items this ShipDepot can store.
     */
    public ShipDepot(int x, int y, int capacity) {
        this(x, y, new StorageUnit(capacity, new MultiTypeStorageUnitStrategy(), new InMemoryStorageUnitContainer()));
    }

    /**
     * Construct a ShipDepot at the specified position with the given StorageUnit.
     *
     * @param x The horizontal coordinate of this ShipDepot.
     * @param y The vertical coordinate of this ShipDepot.
     * @param storageUnit The StorageUnit attached to this ShipDepot.
     */
    public ShipDepot(int x, int y, StorageUnit storageUnit) {
        super(x, y, storageUnit);
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
                "x=" + getX() +
                ", y=" + getY() +
                '}';
    }
}
