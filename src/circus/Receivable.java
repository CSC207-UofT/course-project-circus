package circus;

import circus.inventory.Item;

/**
 * Provides a service for classes that can receive items.
 */
public interface Receivable {
    /**
     * Receive an item.
     * @param item The item to receive.
     * @return True if the item was successfully received, and False otherwise.
     */
    Boolean receiveItem(Item item);
}
