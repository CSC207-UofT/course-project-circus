package circus.warehouse;
import circus.warehouse.Rack;
/**
 * An empty tile in the warehouse.
 */
public class Tile {
    private final int x;
    private final int y;

    /**
     * Construct a WarehouseCell with the given position.
     * @param x The horizontal position of the cell.
     * @param y The vertical position of the cell.
     */
    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

}
