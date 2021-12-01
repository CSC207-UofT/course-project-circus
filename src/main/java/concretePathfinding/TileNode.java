package concretePathfinding;
import complexPathfinder.*;
import warehouse.*;

public class TileNode implements GraphNode {

    private Tile t;

    /**
     * Creates an instance of a TileNode
     *
     * @param tile the tile associated with this node
     */
    public TileNode(Tile tile)
    {
        t = tile;
    }

    /**
     * Returns the ID of the tile, in this case the location in string form.
     * @return a string representing the location/ID of the tile
     */
    @Override
    public String getId() {
        return Integer.toString(t.getX()) + "," + Integer.toString(t.getY());
    }

    /**
     * Getter method for Tile t
     * @return Tile t
     */
    public Tile getT() {
        return t;
    }

}
