package warehouse;

import inventory.Item;
import query.Query;
import query.Queryable;

/**
 * Provides a transaction for distributing items.
 */
public interface Distributable extends Queryable<Item> {
    /**
     *
     * @return An item matching the query, or null if no such item was found.
     */
    Item distributeItem(Query<Item> query);
}
