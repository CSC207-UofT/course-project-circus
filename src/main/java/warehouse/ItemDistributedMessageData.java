package warehouse;

import inventory.Item;

/**
 * Data for the onItemDistributed message.
 */
public record ItemDistributedMessageData(Item item, Distributable distributable) {
    /**
     * Construct ItemDistributedMessageData given an Item and Distributable.
     *
     * @param item The Item that was distributed.
     * @param distributable The Distributable that was modified.
     */
    public ItemDistributedMessageData {
    }
}