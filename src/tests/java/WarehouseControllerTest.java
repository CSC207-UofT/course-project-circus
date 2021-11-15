
import org.junit.jupiter.api.Test;
import warehouse.Tile;
import warehouse.Warehouse;
import inventory.InventoryCatalogue;
import inventory.Item;
import warehouse.WarehouseController;

import static org.junit.jupiter.api.Assertions.*;

public class WarehouseControllerTest {
    @Test
    public void testInsertItem() {
        Warehouse warehouse = new Warehouse(10, 10);
        InventoryCatalogue ic = new InventoryCatalogue();
        Item item = new Item("Cucumber", "A vegetable");
        ic.addItem(item);
        WarehouseController wc = new WarehouseController(warehouse, ic);
        Tile destinationTile = warehouse.findRackFor(item);
        assertEquals(destinationTile, wc.insertItem(item));
    }
}
