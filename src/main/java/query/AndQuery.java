package query;

import java.util.List;

/**
 * A query chaining multiple other queries together with an AND operation.
 * @param <T> The type to query.
 */
public class AndQuery<T> implements Query<T> {
    private final List<Query<T>> queries;

    /**
     * Construct an AndQuery with an array of queries.
     * @param queries The queries to chain.
     */
    public AndQuery(List<Query<T>> queries) {
        this.queries = queries;
    }

    @Override
    public boolean satisfies(T value) {
        for (Query<T> query : queries) {
            if (!query.satisfies(value)) {
                return false;
            }
        }
        return true;
    }
}
