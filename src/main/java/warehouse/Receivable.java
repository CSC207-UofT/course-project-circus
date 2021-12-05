package warehouse;

import warehouse.inventory.Item;
import messaging.Message;

/**
 * Provides a transaction for receiving items.
 */
public interface Receivable {
    /**
     * Receive an item.
     * @param item The item to receive.
     * @return True if the item was successfully received, and False otherwise.
     */
    boolean receiveItem(Item item);

    /**
     * This event is called when this Receivable receives an Item.
     */
    Message<ItemReceivedMessageData> getOnItemReceivedMessage();
}
