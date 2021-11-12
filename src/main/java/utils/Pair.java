package utils;

/**
 * A pair of items.
 */
public class Pair<T, U> {
    private final T first;
    private final U second;

    /**
     * Construct a Pair containing the given items.
     */
    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public U getSecond() {
        return second;
    }
}
