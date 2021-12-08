package pathfinding;

import org.junit.jupiter.api.Test;
import pathfinding.concretePathfinding.*;
import warehouse.Warehouse;
import warehouse.tiles.Rack;
import warehouse.tiles.Tile;
import warehouse.tiles.TileFactory;
import warehouse.tiles.TileType;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test the Pathfinder class.
 */
public class PathfinderControllerTest {
    /**
     * Test the getPath method.
     */
    @Test
    public void testGetPath() {
        // Build the warehouse
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
        // Build the pathfinder
        PathfinderController pathfinder = new PathfinderController(
                warehouse.getTiles(), warehouse.getTileAt(0, 0),
                warehouse.getTileAt(6, 7));

        // The expected path, stored as a 2D array of tile positions.
        int [][] expected = {
                {0, 0},
                {1, 0},
                {2, 0},
                {3, 0},
                {3, 1},
                {3, 2},
                {3, 3},
                {3, 4},
                {3, 5},
                {3, 6},
                {3, 7},
                {4, 7},
                {5, 7},
                {6, 7}
        };

        int i = 0;
        ArrayList<Tile> path = pathfinder.getPath();
        // Make sure each tile in the path matches.
        // realized this doesn't work because the path isn't always calculated the same way
//        for(Tile p : path)
//        {
//            assertEquals(expected[i][0], p.getX());
//            assertEquals(expected[i][1], p.getY());
//            i++;
//            System.out.println(p);
//        }

        assertEquals(expected.length, path.size());

    }


    /**
     * Method to test a pathfinder getting blocked in by racks.
     * This test also checks TileFactory and TileType
     *
     * This should result in an exception being thrown
     */
    @Test
    public void testNoPaths()
    {
        Warehouse warehouse = new Warehouse(10, 10);
        warehouse.setTile(new Rack(0, 1, 5));
        warehouse.setTile(new Rack(1, 0, 5));
        // Build the pathfinder that will not reach its route
        PathfinderController pathfinder = new PathfinderController(
                warehouse.getTiles(), warehouse.getTileAt(0, 0),
                warehouse.getTileAt(6, 7));

        Throwable exception = assertThrows(IllegalStateException.class, () -> pathfinder.getPath());
        assertEquals("No route found", exception.getMessage());


        TileFactory tf = new TileFactory();
        tf.createTile(TileType.EMPTY, 10, 10);
        tf.createTile(TileType.RACK, 9, 9);
        tf.createTile(TileType.RECEIVE_DEPOT, 8, 8);
        tf.createTile(TileType.SHIP_DEPOT, 7, 7);
        
    }
}
