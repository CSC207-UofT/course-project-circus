package main.java;

import main.java.inventory.Item;
import main.java.query.Query;
import main.java.query.Queryable;

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
