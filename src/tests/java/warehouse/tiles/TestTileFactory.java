package warehouse.tiles;

import org.junit.jupiter.api.Test;
import warehouse.tiles.factory.TileFactory;
import warehouse.tiles.factory.TileType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestTileFactory {
    @Test
    public void testCreateTileEmpty1() {
        TileFactory factory = new TileFactory();
        assertTrue(factory.createTile(TileType.EMPTY) instanceof EmptyTile);
    }

    @Test
    public void testCreateTileEmpty2() {
        TileFactory factory = new TileFactory();
        Tile tile = factory.createTile(TileType.EMPTY, 50);
        assertEquals(tile.getIndex(), 50);
        assertTrue(tile instanceof EmptyTile);
    }

    @Test
    public void testCreateTileRack1() {
        TileFactory factory = new TileFactory();
        assertTrue(factory.createTile(TileType.RACK) instanceof Rack);
    }

    @Test
    public void testCreateTileRack2() {
        TileFactory factory = new TileFactory();
        Tile tile = factory.createTile(TileType.RACK, 50);
        assertEquals(tile.getIndex(), 50);
        assertTrue(tile instanceof Rack);
    }

    @Test
    public void testCreateTileReceiveDepot1() {
        TileFactory factory = new TileFactory();
        assertTrue(factory.createTile(TileType.RECEIVE_DEPOT) instanceof ReceiveDepot);
    }

    @Test
    public void testCreateTileReceiveDepot2() {
        TileFactory factory = new TileFactory();
        Tile tile = factory.createTile(TileType.RECEIVE_DEPOT, 50);
        assertEquals(tile.getIndex(), 50);
        assertTrue(tile instanceof ReceiveDepot);
    }

    @Test
    public void testCreateTileShipDepot1() {
        TileFactory factory = new TileFactory();
        assertTrue(factory.createTile(TileType.SHIP_DEPOT) instanceof ShipDepot);
    }

    @Test
    public void testCreateTileShipDepot2() {
        TileFactory factory = new TileFactory();
        Tile tile = factory.createTile(TileType.SHIP_DEPOT, 50);
        assertEquals(tile.getIndex(), 50);
        assertTrue(tile instanceof ShipDepot);
    }
}
