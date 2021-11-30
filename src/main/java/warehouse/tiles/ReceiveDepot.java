package warehouse.tiles;

import messaging.Message;
import query.Query;
import warehouse.inventory.Item;
import warehouse.storage.StorageUnit;
import warehouse.transactions.Distributable;
import warehouse.transactions.ItemDistributedMessageData;

import java.util.List;

/**
 * Receives items from the outside world.
 */
public class ReceiveDepot extends StorageTile implements Distributable {
    private final Message<ItemDistributedMessageData> onItemDistributedMessage;

    /**
     * Construct a ReceiveDepot at the specified position with the given StorageUnit.
     *
     * @param x The horizontal coordinate of this StorageTile.
     * @param y The vertical coordinate of this StorageTile.
     * @param storageUnit The StorageUnit attached to this StorageTile.
     */
    public ReceiveDepot(int x, int y, StorageUnit storageUnit) {
        super(x, y, storageUnit);
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
}
