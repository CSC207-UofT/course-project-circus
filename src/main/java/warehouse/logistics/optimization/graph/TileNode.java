package warehouse.logistics.optimization.graph;

import warehouse.tiles.EmptyTile;
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
        return Integer.toString(tile.getIndex());
    }

    /**
     * Get the score multiplier. This is binary: 1 if empty, and infinity if non-empty.
     */
    @Override
    public double getScoreMultiplier() {
        return tile instanceof EmptyTile ? 1 : Double.MAX_VALUE;
    }

    /**
     * Getter method for the tile
     */
    public Tile getTile() {
        return tile;
    }

    @Override
    public String toString() {
        return "TileNode{" +
                "tile=" + tile +
                '}';
    }
}
