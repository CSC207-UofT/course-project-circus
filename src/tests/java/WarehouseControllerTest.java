import inventory.InventoryCatalogue;
import inventory.Item;
import org.junit.jupiter.api.Test;
import warehouse.Tile;
import warehouse.Warehouse;
import warehouse.WarehouseController;
import warehouse.inventory.PartCatalogue;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WarehouseControllerTest {
    @Test
    public void testInsertItem() {
        Warehouse warehouse = new Warehouse(10, 10);
        PartCatalogue ic = new PartCatalogue();
        Part p = new Part("Cucumber", "A vegetable");
        ic.addItem(item);
        WarehouseController wc = new WarehouseController(warehouse, ic);
        Tile destinationTile = warehouse.findRackFor(item);
        assertEquals(destinationTile, wc.insertItem(item));
    }
}
