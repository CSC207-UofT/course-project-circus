package warehouse.tiles;

import messaging.Message;
import warehouse.inventory.Item;
import warehouse.storage.StorageUnit;
import warehouse.transactions.ItemReceivedMessageData;
import warehouse.transactions.Receivable;

/**
 * Ships items from the warehouse to the outside world.
 */
public class ShipDepot extends StorageTile implements Receivable {
    private final Message<ItemReceivedMessageData> onItemReceivedMessage;

    /**
     * Construct a ShipDepotShipDepotTile at the specified position with the given StorageUnit.
     *
     * @param x The horizontal coordinate of this StorageTile.
     * @param y The vertical coordinate of this StorageTile.
     * @param storageUnit The StorageUnit attached to this StorageTile.
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
}
