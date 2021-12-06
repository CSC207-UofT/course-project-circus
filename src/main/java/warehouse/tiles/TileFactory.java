package warehouse.tiles;

/**
 * A simple factory for creating Warehouse tiles.
 */
public class TileFactory {
    /**
     * Create a tile of the given type with default options.
     * @param type The type of tile to create.
     * @param x The horizontal coordinate of the tile.
     * @param y The vertical coordinate of the tile.
     * @return A Tile object representing the newly created Tile, or null if the type is not valid.
     */
    public Tile createTile(TileType type, int x, int y) {
        switch (type) {
            case EMPTY:
                return new EmptyTile(x, y);
            case RACK:
                return new Rack(x, y);
            case RECEIVE_DEPOT:
                return new ReceiveDepot(x, y);
            case SHIP_DEPOT:
                return new ShipDepot(x, y);
        }
        return null;
    }
}
