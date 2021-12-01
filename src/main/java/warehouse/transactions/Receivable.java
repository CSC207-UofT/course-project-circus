package warehouse.transactions;

import warehouse.inventory.Item;
import messaging.Message;
import warehouse.tiles.Tile;

/**
 * Provides a contract for receiving items.
 */
public interface Receivable {
    /**
     * Receive an item.
     * @param item The item to receive.
     * @return True if the item was successfully received, and False otherwise.
     */
    boolean receiveItem(Item item);

    /**
     * Get the Tile corresponding to this Receivable.
     * @return a Tile object representing the physical location of this Receivable in the Warehouse.
     */
    Tile getTile();

    /**
     * This event is called when this Receivable receives an Item.
     */
    Message<ItemReceivedMessageData> getOnItemReceivedMessage();
}
