package warehouse;

/**
 * An exception that is thrown when a tile coordinate is out of bounds.
 */
public class TileOutOfBoundsException extends Exception {
    /**
     * Construct a TileOutOfBoundsException with coordinates and bounds.
     * @param x The horizontal coordinate of the tile.
     * @param y The vertical coordinate of the tile.
     * @param width The upper bound on the horizontal coordinate.
     * @param height The upper bound on the vertical coordinate.
     */
    public TileOutOfBoundsException(int x, int y, int width, int height) {
        super(String.format("Tile at (%d, %d) is out of bounds for layout of size (%d, %d).",
                x, y, width, height));
    }
}
