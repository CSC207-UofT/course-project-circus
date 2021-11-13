package tests.java;

import warehouse.Warehouse;
import warehouse.Tile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DepotTest {
    Warehouse w;

    @Test
    public void testGetTileAt() {
        Warehouse w = new Warehouse(10, 10);
        assertEquals(w.tiles[0][0], w.getTileAt(0, 0));
        assertThrows(TileOutOfBoundException.class, () -> {
            w.getTileAt(11, 10);
        });
    }

    @Test
    public void testFindEmptyTile() {
        Warehouse w = new Warehouse(10, 10);
        assertEquals(w.tiles[0][0], w.findEmptyTile());
        Depot d1 = new Depot();
        w.tiles[0][0].setStorageUnit(d1);
        assertEquals(w.tiles[0][1], w.findEmptyTile());
    }

    @Test
    public void testFindRackFor() {
        Warehouse w = new Warehouse(10, 10);
        Rack r1 = new Rack();
        w.tiles[8][3].setStorageUnit(r1);
        Item item1 = new Item("Cucumber", "A vegetable.");
        assertEquals(w.tiles[8][3], w.findRackFor(item1));
    }

    @Test
    public void testIsTileCoordinateInRange() {
        Warehouse w = new Warehouse(10, 10);
        assertTrue(w.isTileCoordinateInRange(0, 5));
        assertFalse(w.isTileCoordinateInRange(5, 15));
    }
}
