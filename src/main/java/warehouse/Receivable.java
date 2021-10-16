package warehouse;

import inventory.Item;

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
}
