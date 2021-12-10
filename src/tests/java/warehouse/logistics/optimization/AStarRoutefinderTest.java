package warehouse.logistics.optimization;

import application.shell.presenters.warehouse.GridWarehousePresenter;
import org.junit.jupiter.api.Test;
import warehouse.Warehouse;
import warehouse.WarehouseLayout;
import warehouse.WarehouseState;
import warehouse.geometry.grid.GridWarehouseCoordinateSystem;
import warehouse.geometry.grid.Point;
import warehouse.inventory.PartCatalogue;
import warehouse.logistics.optimization.graph.Graph;
import warehouse.logistics.optimization.graph.TileNode;
import warehouse.logistics.optimization.graph.converters.SimpleWarehouseGraphConverter;
import warehouse.logistics.optimization.routefinding.algorithms.AStarRoutefinder;
import warehouse.logistics.orders.OrderQueue;
import warehouse.robots.RobotMapper;
import warehouse.tiles.Rack;
import warehouse.tiles.Tile;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test the AStarRoutefinder class.
 */
public class AStarRoutefinderTest {
    /**
     * Test the getPath method.
     */
    @Test
    public void testGetPath() {
        // Create an empty 10x10 warehouse
        GridWarehouseCoordinateSystem coordinateSystem = new GridWarehouseCoordinateSystem(10, 10);
        Warehouse<GridWarehouseCoordinateSystem, Point> warehouse = new Warehouse<>(new WarehouseState<>(
                new PartCatalogue(),
                coordinateSystem,
                new WarehouseLayout<>(coordinateSystem),
                new RobotMapper<>(coordinateSystem),
                new OrderQueue()
        ));
        WarehouseLayout<Point> layout = warehouse.getState().getLayout();
        layout.setTileAt(new Point(0, 1), new Rack());
        layout.setTileAt(new Point(1, 1), new Rack());
        layout.setTileAt(new Point(2, 1), new Rack());
        layout.setTileAt(new Point(4, 6), new Rack());
        layout.setTileAt(new Point(0, 8), new Rack());
        layout.setTileAt(new Point(2, 2), new Rack());
        layout.setTileAt(new Point(2, 3), new Rack());
        layout.setTileAt(new Point(2, 4), new Rack());
        layout.setTileAt(new Point(5, 4), new Rack());
        layout.setTileAt(new Point(4, 5), new Rack());
        // Build the graph
        SimpleWarehouseGraphConverter<GridWarehouseCoordinateSystem, Point> warehouseGraphConverter =
                new SimpleWarehouseGraphConverter<>();

        Graph<TileNode> graph = warehouseGraphConverter.convert(warehouse);
        DistanceTileScorer<GridWarehouseCoordinateSystem, Point> metric = new DistanceTileScorer<>(layout.getCoordinateSystem());
        // The expected route, stored as a 2D array of tile positions.
        int [][] expected = {
                {1, 0},
                {2, 0},
                {3, 1},
                {4, 2},
                {5, 3},
                {6, 4},
                {6, 5},
                {6, 6},
                {6, 7}
        };
        // Build the routefinder with euclidean distance metrics
        AStarRoutefinder<TileNode> routefinder = new AStarRoutefinder<>(graph, metric, metric);
        // Find path between (0, 0) and (6, 7)
        TileNode source = new TileNode(layout.getTileAt(new Point(0, 0)));
        TileNode destination = new TileNode(layout.getTileAt(new Point(6, 7)));
        List<TileNode> route = routefinder.findRoute(source, destination);
        // NOTE: This is not a reliable way to check if the route is valid, since the A* algorithm
        // might give a different path that has an equivalent cost (i.e. it is not fully deterministic).
        for (int i = 0, pathSize = route.size(); i < pathSize; i++) {
            Tile tile = route.get(i).getTile();
            Point point = coordinateSystem.projectIndexToCoordinate(tile.getIndex());
            assertEquals(expected[i][0], point.getX());
            assertEquals(expected[i][1], point.getY());
        }

        assertEquals(expected.length, route.size());

    }


    /**
     * Method to test a pathfinder getting blocked in by racks.
     * This test also checks TileFactory and TileType
     *
     * This should result in an exception being thrown
     */
//    @Test
//    public void testNoPaths()
//    {
//        Warehouse warehouse = new Warehouse(10, 10);
//        warehouse.setTile(new Rack(0, 1, 5));
//        warehouse.setTile(new Rack(1, 0, 5));
//        // Build the pathfinder that will not reach its route
//        PathfinderController pathfinder = new PathfinderController(
//                warehouse.getTiles(), warehouse.getTileAt(0, 0),
//                warehouse.getTileAt(6, 7));
//
//        Throwable exception = assertThrows(IllegalStateException.class, () -> pathfinder.getPath());
//        assertEquals("No route found", exception.getMessage());
//
//
//        TileFactory tf = new TileFactory();
//        tf.createTile(TileType.EMPTY, 10, 10);
//        tf.createTile(TileType.RACK, 9, 9);
//        tf.createTile(TileType.RECEIVE_DEPOT, 8, 8);
//        tf.createTile(TileType.SHIP_DEPOT, 7, 7);
//    }
}
