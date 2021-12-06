package pathfinding;

import org.junit.jupiter.api.Test;
import pathfinding.concretePathfinding.*;
import warehouse.Warehouse;
import warehouse.tiles.Rack;
import warehouse.tiles.Tile;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

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
        int [][] expected = {{0, 0}, {1, 0}, {2, 0}, {3, 0}, {3, 1}, {3, 2}, {3, 3}, {3, 4}, {3,5}, {3, 6}, {3, 7},
                {4, 7}, {5, 7}, {6, 7}};
        int i = 0;
        ArrayList<Tile> path = check.getPath();
        for(Tile p: path)
        {
            assertEquals(expected[i][0], p.getX());
            assertEquals(expected[i][1], p.getY());
            i++;
        }

    }
}
