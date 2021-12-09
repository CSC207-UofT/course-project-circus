package warehouse.tiles;

import messaging.Message;
import query.Query;
import warehouse.inventory.Item;
import warehouse.storage.StorageUnit;
import warehouse.storage.containers.InMemoryStorageUnitContainer;
import warehouse.storage.strategies.MultiTypeStorageUnitStrategy;
import warehouse.transactions.Distributable;
import warehouse.transactions.ItemDistributedMessageData;

import java.util.List;

/**
 * Receives items from the outside world.
 */
public class ReceiveDepot extends StorageTile implements Distributable {
    private final Message<ItemDistributedMessageData> onItemDistributedMessage;

    /**
     * Construct an unassociated ReceiveDepot, e.g. with index -1, at the specified index with an infinite capacity multi-type in-memory StorageUnit.
     */
    public ReceiveDepot() {
        this(-1, -1);
    }

    /**
     * Construct a ReceiveDepot at the specified index with an infinite capacity multi-type in-memory StorageUnit.
     *
     * @param index The index of this ReceiveDepot.
     */
    public ReceiveDepot(int index) {
        this(index, -1);
    }

    /**
     * Construct a ReceiveDepot at the specified index with a multi-type in-memory StorageUnit with the given capacity.
     *
     * @param index The index of this ReceiveDepot.
     * @param capacity Maximum number of Items this ReceiveDepot can store.
     */
    public ReceiveDepot(int index, int capacity) {
        this(index, new StorageUnit(capacity, new MultiTypeStorageUnitStrategy(), new InMemoryStorageUnitContainer()));
    }

    /**
     * Construct a ReceiveDepot at the specified index with the given StorageUnit.
     *
     * @param index The index of this ReceiveDepot.
     * @param storageUnit The StorageUnit attached to this ReceiveDepot.
     */
    public ReceiveDepot(int index, StorageUnit storageUnit) {
        super(index, storageUnit);
        onItemDistributedMessage = new Message<>();
    }

    @Override
    public Iterable<Item> getQueryItems() {
        return storageUnit.getContainer().getItems();
    }

    @Override
    public Item distributeItem(Query<Item> itemQuery) {
        List<Item> queryResult = query(itemQuery);
        if (queryResult.size() == 0) {
            // Throw exception
            return null;
        }
        // Try removing the item
        Item item = queryResult.get(0);
        if (!storageUnit.removeItem(item)) {
            return null;
        }
        return item;
    }

    @Override
    public Tile getTile() {
        return this;
    }

    @Override
    public Message<ItemDistributedMessageData> getOnItemDistributedMessage() {
        return onItemDistributedMessage;
    }

    @Override
    public String toString() {
        return "ReceiveDepot{" +
                "storageUnit=" + storageUnit +
                ", index=" + index +
                '}';
    }
}
