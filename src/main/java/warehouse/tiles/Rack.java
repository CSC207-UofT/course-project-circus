package warehouse.tiles;

import messaging.Message;
import query.Query;
import warehouse.inventory.Item;
import warehouse.storage.StorageUnit;
import warehouse.storage.containers.InMemoryStorageUnitContainer;
import warehouse.storage.strategies.SingleTypeStorageStrategy;
import warehouse.transactions.Distributable;
import warehouse.transactions.ItemDistributedMessageData;
import warehouse.transactions.ItemReceivedMessageData;
import warehouse.transactions.Receivable;

import java.util.List;

/**
 * Stores items in the warehouse.
 */
public class Rack extends StorageTile implements Receivable, Distributable {
    private final Message<ItemReceivedMessageData> onItemReceivedMessage;
    private final Message<ItemDistributedMessageData> onItemDistributedMessage;

    /**
     * Construct a Rack at the specified position with an infinite capacity single-type in-memory StorageUnit.
     *
     * @param x The horizontal coordinate of this Rack.
     * @param y The vertical coordinate of this Rack.
     */
    public Rack(int x, int y) {
        this(x, y, -1);
    }

    /**
     * Construct a Rack at the specified position with a single-type in-memory StorageUnit with the given capacity.
     *
     * @param x The horizontal coordinate of this Rack.
     * @param y The vertical coordinate of this Rack.
     * @param capacity Maximum number of Items this Rack can store.
     */
    public Rack(int x, int y, int capacity) {
        this(x, y, new StorageUnit(capacity, new SingleTypeStorageStrategy(), new InMemoryStorageUnitContainer()));
    }

    /**
     * Construct a Rack at the specified position with the given StorageUnit.
     *
     * @param x The horizontal coordinate of this Rack.
     * @param y The vertical coordinate of this Rack.
     * @param storageUnit The StorageUnit attached to this Rack.
     */
    public Rack(int x, int y, StorageUnit storageUnit) {
        super(x, y, storageUnit);
        onItemReceivedMessage = new Message<>();
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
    public Message<ItemDistributedMessageData> getOnItemDistributedMessage() {
        return onItemDistributedMessage;
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
}
