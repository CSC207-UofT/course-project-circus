package main.java.query;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides a service for classes that can perform queries.
 */
public interface Queryable<T> {
    /**
     * Find all values satisfying the given Query.
     * @param query The query to perform.
     * @return The values satisfying the given Query.
     */
    default List<T> query(Query<T> query) {
        Iterable<T> queryItems = getQueryItems();
        List<T> queryResult = new ArrayList<>();
        for (T queryItem : queryItems) {
            if (!query.satisfies(queryItem)) continue;
            queryResult.add(queryItem);
        }
        return queryResult;
    }

    /**
     * Get the items that should be queried.
     * @return An Iterable containing the query items.
     */
    Iterable<T> getQueryItems();
}
