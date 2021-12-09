package pathfinding.concretePathfinding;
import pathfinding.complexPathfinder.*;
import warehouse.tiles.Tile;

public class TileNode implements GraphNode {
    private final Tile tile;

    /**
     * Creates an instance of a TileNode
     *
     * @param tile the tile associated with this node
     */
    public TileNode(Tile tile)
    {
        this.tile = tile;
    }

    /**
     * Returns the ID of the tile, in this case the location in string form.
     * @return a string representing the location/ID of the tile
     */
    @Override
    public String getId() {
        //TODO:
        //return tile.getX() + "," + tile.getY();
        return null;
    }

    /**
     * Getter method for the tile
     */
    public Tile getTile() {
        return tile;
    }

}
