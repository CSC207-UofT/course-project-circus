package warehouse;

import warehouse.inventory.Item;

/**
 * Data for the onItemDistributed message.
 */
public class ItemDistributedMessageData {
    private final Item item;
    private final Distributable distributable;

    /**
     * Construct ItemDistributedMessageData given an Item and Distributable.
     *
     * @param item The Item that was distributed.
     * @param distributable The Distributable that was modified.
     */
    public ItemDistributedMessageData(Item item, Distributable distributable) {
        this.item = item;
        this.distributable = distributable;
    }

    public Item getItem() {
        return item;
    }

    public Distributable getDistributable() {
        return distributable;
    }
}