package warehouse;

import inventory.Item;

/**
 * Data for the onItemReceived message.
 */
public class ItemReceivedMessageData {
    private final Item item;
    private final Receivable receivable;

    /**
     * Construct ItemReceivedMessageData given an Item and Receivable.
     *
     * @param item The Item that was received.
     * @param receivable The Receivable that was modified.
     */
    public ItemReceivedMessageData(Item item, Receivable receivable) {
        this.item = item;
        this.receivable = receivable;
    }

    public Item getItem() {
        return item;
    }

    public Receivable getReceivable() {
        return receivable;
    }
}