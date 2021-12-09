package warehouse.tiles;

/**
 * A strategy for determining whether a given Tile is empty.
 */
public interface EmptyTileChecker {
    /**
     * Return whether the given Tile is empty.
     * @param tile The Tile to check.
     * @return True if the given Tile is empty, and False otherwise.
     */
    boolean isEmpty(Tile tile);
}
