package pathfinding;

import org.junit.jupiter.api.Test;
import pathfinding.concretePathfinding.*;
import warehouse.WarehouseLayout;
import warehouse.geometry.grid.GridWarehouseCoordinateSystem;
import warehouse.geometry.grid.Point;
import warehouse.tiles.Rack;
import warehouse.tiles.Tile;

import java.util.ArrayList;

/**
 * Test the Pathfinder class.
 */
public class PathfinderControllerTest {
    /**
     * Test the getPath method.
     */
    @Test
    public void testGetPath() {
        // Build the warehouseLayout
        WarehouseLayout<Point> warehouseLayout = new WarehouseLayout<>(
                new GridWarehouseCoordinateSystem(10, 10));

        warehouseLayout.setTileAt(new Point(0, 1), new Rack());
        warehouseLayout.setTileAt(new Point(1, 1), new Rack());
        warehouseLayout.setTileAt(new Point(2, 1), new Rack());
        warehouseLayout.setTileAt(new Point(4, 6), new Rack());
        warehouseLayout.setTileAt(new Point(0, 8), new Rack());
        warehouseLayout.setTileAt(new Point(2, 2), new Rack());
        warehouseLayout.setTileAt(new Point(2, 3), new Rack());
        warehouseLayout.setTileAt(new Point(2, 4), new Rack());
        warehouseLayout.setTileAt(new Point(5, 4), new Rack());
        warehouseLayout.setTileAt(new Point(4, 5), new Rack());
        // Build the pathfinder
//        PathfinderController pathfinder = new PathfinderController(
//                warehouseLayout.getTiles(), warehouseLayout.getTileAt(0, 0),
//                warehouseLayout.getTileAt(6, 7));

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

//        int i = 0;
//        ArrayList<Tile> path = pathfinder.getPath();
//        // Make sure each tile in the path matches.
//        for(Tile p : path)
//        {
//            assertEquals(expected[i][0], p.getX());
//            assertEquals(expected[i][1], p.getY());
//            i++;
//        }
    }
}
