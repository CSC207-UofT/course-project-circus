package circus;

import circus.inventory.Item;
import circus.query.Query;
import circus.query.Queryable;

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
