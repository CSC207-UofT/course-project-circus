package warehouse.tiles.factory;

import warehouse.tiles.*;

/**
 * A simple factory for creating tiles.
 */
public class TileFactory {
    /**
     * Create a tile of the given type with default options.
     * @param type The type of tile to create.
     * @param index The index of the Tile.
     * @return A Tile object representing the newly created Tile, or null if the type is not valid.
     */
    public Tile createTile(TileType type, int index) {
        switch (type) {
            case EMPTY:
                return new EmptyTile(index);
            case RACK:
                return new Rack(index);
            case RECEIVE_DEPOT:
                return new ReceiveDepot(index);
            case SHIP_DEPOT:
                return new ShipDepot(index);
        }
        return null;
    }

    /**
     * Create an unassociated tile, e.g. with index -1, of the given type with default options.
     * @param type The type of tile to create.
     * @return A Tile object representing the newly created Tile, or null if the type is not valid.
     */
    public Tile createTile(TileType type) {
        return createTile(type, -1);
    }
}
