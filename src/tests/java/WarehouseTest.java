import inventory.Item;
import warehouse.TileOutOfBoundsException;
import warehouse.Warehouse;
import warehouse.Tile;
import org.junit.jupiter.api.Test;
import warehouse.storage.Rack;

import static org.junit.jupiter.api.Assertions.*;

public class WarehouseTest {
    Warehouse w;

    @Test
    public void testGetTileAt() {
        Warehouse w = new Warehouse(10, 10);
        try {
            assertEquals(w.getTileAt(0, 0), w.getTileAt(0, 0));
        } catch (TileOutOfBoundsException e) {
            e.printStackTrace();
        }
        assertThrows(TileOutOfBoundsException.class, () -> {
            w.getTileAt(11, 10);
        });
    }

    @Test
    public void testFindRackFor() throws TileOutOfBoundsException {
        Warehouse w = new Warehouse(10, 10);
        Rack r1 = new Rack();
        Tile tile = w.getTileAt(8, 3);
        tile.setStorageUnit(r1);
        Item item1 = new Item("Cucumber", "A vegetable.");
        assertEquals(tile, w.findRackFor(item1));
    }

    @Test
    public void testIsTileCoordinateInRange() {
        Warehouse w = new Warehouse(10, 10);
        assertTrue(w.isTileCoordinateInRange(0, 5));
        assertFalse(w.isTileCoordinateInRange(5, 15));
    }
}
