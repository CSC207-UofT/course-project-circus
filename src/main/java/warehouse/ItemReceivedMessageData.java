package warehouse;

import inventory.Item;

/**
 * Data for the onItemReceived message.
 */
public record ItemReceivedMessageData(Item item, Receivable receivable) {
    /**
     * Construct ItemReceivedMessageData given an Item and Receivable.
     *
     * @param item The Item that was received.
     * @param receivable The Receivable that was modified.
     */
    public ItemReceivedMessageData {
    }
}