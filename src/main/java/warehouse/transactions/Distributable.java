package warehouse.transactions;

import warehouse.inventory.Item;
import messaging.Message;
import query.Query;
import query.Queryable;
import warehouse.tiles.Tile;

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
     * Get the Tile corresponding to this Distributable.
     * @return a Tile object representing the physical location of this Distributable in the WarehouseLayout.
     */
    Tile getTile();

    /**
     * This event is called when this Distributable distributes an Item.
     */
    Message<ItemDistributedMessageData> getOnItemDistributedMessage();
}
