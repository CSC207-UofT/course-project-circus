package warehouse.tiles;

import messaging.Message;
import query.Query;
import warehouse.transactions.ItemDistributedMessageData;
import warehouse.transactions.ItemReceivedMessageData;
import warehouse.inventory.Item;
import warehouse.storage.StorageUnit;
import warehouse.transactions.Distributable;
import warehouse.transactions.Receivable;

import java.util.List;

/**
 * A Tile that has a StorageUnit on it.
 */
public class StorageTile extends Tile implements Receivable, Distributable {
    private StorageUnit storageUnit;
    private final Message<ItemReceivedMessageData> onItemReceivedMessage;
    private final Message<ItemDistributedMessageData> onItemDistributedMessage;

    /**
     * Construct a StorageTile at the specified position with the given StorageUnit.
     * @param x The horizontal coordinate of this StorageTile.
     * @param y The vertical coordinate of this StorageTile.
     * @param storageUnit the StorageUnit attached to this StorageTile.
     */
    public StorageTile(int x, int y, StorageUnit storageUnit) {
        super(x, y);
        this.storageUnit = storageUnit;
        this.onItemReceivedMessage = new Message<>();
        this.onItemDistributedMessage = new Message<>();
    }

    /**
     * Checks to see if the tile contains a storage unit.
     * @return True if the tile does not contain a storage unit, otherwise false.
     */
    public boolean isEmpty()
    {
        return storageUnit == null;
    }

    public StorageUnit getStorageUnit() {
        return storageUnit;
    }


    public void setStorageUnit(StorageUnit storageUnit) {
        this.storageUnit = storageUnit;
    }

    @Override
    public Iterable<Item> getQueryItems() {
        return storageUnit.getContainer().getItems();
    }

    @Override
    public boolean receiveItem(Item item) {
        return storageUnit.addItem(item);
    }

    @Override
    public Message<ItemReceivedMessageData> getOnItemReceivedMessage() {
        return onItemReceivedMessage;
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
}
