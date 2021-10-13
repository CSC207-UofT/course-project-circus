package circus.query;

/**
 * An interface representing a query for a Queryable.
 * @param <T> The type to query.
 */
public interface Query<T> {
    /**
     * Check if the given value satisfies this Query.
     * @param value The value to check.
     * @return True if the value satisfies this Query, and False otherwise.
     */
    boolean satisfies(T value);
}
