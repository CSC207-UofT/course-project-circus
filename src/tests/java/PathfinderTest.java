import org.junit.jupiter.api.Test;
import concretePathfinding.*;
import complexPathfinder.*;
import warehouse.Tile;
import warehouse.TileOutOfBoundsException;
import warehouse.Warehouse;
import warehouse.WarehouseController;
import warehouse.inventory.Item;
import warehouse.inventory.Part;
import warehouse.inventory.PartCatalogue;
import warehouse.storage.Rack;

import static org.junit.jupiter.api.Assertions.*;

public class PathfinderTest {

    @Test
    public void testPathfinder() throws TileOutOfBoundsException {
//        Warehouse warehouse = new Warehouse(10, 10);
//        PartCatalogue ic = new PartCatalogue();
//        Part p = new Part("Cucumber", "A vegetable");
//        ic.addPart(p);
//        WarehouseController wc = new WarehouseController(warehouse, ic);
//        Item item = new Item(p);
//        Tile destinationTile = warehouse.findRackFor(item);
//        assertEquals(destinationTile, wc.insertItem(item));

        Warehouse map = new Warehouse(10, 10);
        map.getTileAt(0,1).setStorageUnit(new Rack(5));
        map.getTileAt(1,1).setStorageUnit(new Rack(5));
        map.getTileAt(2,1).setStorageUnit(new Rack(5));


//        Set nodes = map.
//
//        Graph g = new Graph()
    }
}
