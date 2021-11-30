package warehouse.transactions;

import warehouse.inventory.Item;
import messaging.Message;
import query.Query;
import query.Queryable;

/**
 * Provides a contract for distributing items.
 */
public interface Distributable extends Queryable<Item> {
    /**
     *
     * @return An item matching the query, or null if no such item was found.
     */
    Item distributeItem(Query<Item> query);

    /**
     * This event is called when this Distributable distributes an Item.
     */
    Message<ItemDistributedMessageData> getOnItemDistributedMessage();
}
