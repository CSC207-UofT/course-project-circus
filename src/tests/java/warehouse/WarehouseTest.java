package warehouse;

import org.junit.jupiter.api.Test;
import warehouse.Warehouse;

import static org.junit.jupiter.api.Assertions.*;

public class WarehouseTest {
    @Test
    public void testGetTileAt() {
        Warehouse warehouse = new Warehouse(10, 10);
        assertEquals(warehouse.getTileAt(0, 0), warehouse.getTileAt(0, 0));
        assertNull(warehouse.getTileAt(11, 10));
    }

    @Test
    public void testIsTileCoordinateInRange() {
        Warehouse warehouse = new Warehouse(10, 10);
        assertTrue(warehouse.isTileCoordinateInRange(0, 5));
        assertFalse(warehouse.isTileCoordinateInRange(5, 15));
    }
}
