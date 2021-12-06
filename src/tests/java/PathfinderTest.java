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

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PathfinderTest {

    @Test
    public void testPathfinder() throws TileOutOfBoundsException {

        PartCatalogue catalogue = new PartCatalogue();
        Warehouse map = new Warehouse(10, 10);
        map.getTileAt(0, 1).setStorageUnit(new Rack(5));
        map.getTileAt(1, 1).setStorageUnit(new Rack(5));
        map.getTileAt(2, 1).setStorageUnit(new Rack(5));
        PathfinderController check = new PathfinderController(map.getTiles(), map.getTileAt(0, 0), map.getTileAt(0, 0));
        GraphCreator graphCreator = new GraphCreator(map.getTiles());
        System.out.print(check.getPath());

    }
}
