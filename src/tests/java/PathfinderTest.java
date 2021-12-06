import org.junit.jupiter.api.Test;
import concretePathfinding.*;
import warehouse.Warehouse;
import warehouse.tiles.Rack;

/**
 * Test the Pathfinder class.
 */
public class PathfinderTest {
    /**
     *
     */
    @Test
    public void testGetPath() {
        Warehouse warehouse = new Warehouse(10, 10);
        warehouse.setTile(new Rack(0, 1, 5));
        warehouse.setTile(new Rack(1, 1, 5));
        warehouse.setTile(new Rack(2, 1, 5));
        warehouse.setTile(new Rack(4, 6, 5));
        warehouse.setTile(new Rack(0, 8, 5));
        warehouse.setTile(new Rack(2, 2, 5));
        warehouse.setTile(new Rack(2, 3, 5));
        warehouse.setTile(new Rack(2, 4, 5));
        warehouse.setTile(new Rack(5, 4, 5));
        warehouse.setTile(new Rack(4, 5, 5));

        PathfinderController check = new PathfinderController(
                warehouse.getTiles(), warehouse.getTileAt(0, 0),
                warehouse.getTileAt(6, 7));

        System.out.print(check.getPath());

    }
}
