package application.shell.presenters;

import application.shell.presenters.warehouse.GridWarehousePresenter;
import org.junit.jupiter.api.Test;
import warehouse.WarehouseLayout;
import warehouse.WarehouseState;
import warehouse.geometry.grid.GridWarehouseCoordinateSystem;
import warehouse.geometry.grid.Point;
import warehouse.inventory.PartCatalogue;
import warehouse.logistics.orders.OrderQueue;
import warehouse.robots.RobotMapper;
import warehouse.tiles.Rack;
import warehouse.tiles.ReceiveDepot;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GridWarehousePresenterTest {
    @Test
    public void testConvert() {
        // Create an empty 10x10 warehouse
        GridWarehouseCoordinateSystem coordinateSystem = new GridWarehouseCoordinateSystem(10, 10);
        WarehouseState<GridWarehouseCoordinateSystem, Point> state = new WarehouseState<>(
                new PartCatalogue(),
                coordinateSystem,
                new WarehouseLayout<>(coordinateSystem),
                new RobotMapper<>(coordinateSystem),
                new OrderQueue()
        );
        WarehouseLayout<Point> layout = state.getLayout();
        // Add a rack at (0, 0)
        layout.setTileAt(new Point(0, 0), new Rack());
        // Add a rack at (5, 0)
        layout.setTileAt(new Point(5, 0), new Rack());
        // Add a receive depot at (5, 5)
        layout.setTileAt(new Point(5, 5), new ReceiveDepot());
        // Create presenter
        GridWarehousePresenter presenter = new GridWarehousePresenter();
        String expected = "======================\n" +
                          "EmptyTile        .\n" +
                          "Rack             X\n" +
                          "ReceiveDepot     R\n" +
                          "ShipDepot        S\n" +
                          "======================\n" +
                          "   0 1 2 3 4 5 6 7 8 9\n" +
                          " 0 X . . . . X . . . .\n" +
                          " 1 . . . . . . . . . .\n" +
                          " 2 . . . . . . . . . .\n" +
                          " 3 . . . . . . . . . .\n" +
                          " 4 . . . . . . . . . .\n" +
                          " 5 . . . . . R . . . .\n" +
                          " 6 . . . . . . . . . .\n" +
                          " 7 . . . . . . . . . .\n" +
                          " 8 . . . . . . . . . .\n" +
                          " 9 . . . . . . . . . .";
        assertEquals(expected.strip(), presenter.convert(state).strip());
    }
}
