package application.shell.commands;

import application.shell.ShellApplication;
import application.shell.commands.framework.ShellCommand;
import application.shell.commands.framework.ShellCommandExecutor;
import application.shell.presenters.coordinateparsers.PointParser;
import org.junit.jupiter.api.Test;
import warehouse.Warehouse;
import warehouse.WarehouseLayout;
import warehouse.WarehouseState;
import warehouse.geometry.grid.GridWarehouseCoordinateSystem;
import warehouse.geometry.grid.Point;
import warehouse.inventory.PartCatalogue;
import warehouse.logistics.orders.OrderQueue;
import warehouse.robots.RobotMapper;
import warehouse.tiles.Rack;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DisplayStorageUnitInfoCommandTest {
    @Test
    public void testDisplayStorageUnitInfoCommand() {
        // Create an empty 10x10 warehouse
        GridWarehouseCoordinateSystem coordinateSystem = new GridWarehouseCoordinateSystem(10, 10);
        Warehouse<GridWarehouseCoordinateSystem, Point> warehouse = new Warehouse<>(new WarehouseState<>(
                new PartCatalogue(),
                coordinateSystem,
                new WarehouseLayout<>(coordinateSystem),
                new RobotMapper<>(coordinateSystem),
                new OrderQueue()
        ));
        // Add rack at (5, 5)
        Rack rack = new Rack(); // Create new Rack
        warehouse.getState().getLayout().setTileAt(new Point(5, 5), rack); // Associate rack with the coordinate (5,5)
        // Create shell application and command
        ShellApplication<GridWarehouseCoordinateSystem, Point> application = new ShellApplication<>(
                warehouse, null, new PointParser(), null);

        DisplayStorageUnitInfoCommand<GridWarehouseCoordinateSystem, Point> command = new DisplayStorageUnitInfoCommand<>();
        ShellCommandExecutor commandExecutor = new ShellCommandExecutor(application, new ShellCommand[]{command});
        // We need to remember to surround the argument in quotes since it has a space!
        String output1 = commandExecutor.execute("display-storage-unit-info (0, 0)");
        assertEquals("Could not parse coordinate: (0,", output1);
        // The tile at (0, 0) is empty, so we should get an error!
        String output2 = commandExecutor.execute("display-storage-unit-info '(0, 0)'");
        assertEquals("the tile at (0, 0) is not a StorageTile", output2);
        // The tile at (5, 5) is a rack!
        String output3 = commandExecutor.execute("display-storage-unit-info '(5, 5)'");
        assertEquals(output3, rack.getStorageUnit().toString());
    }
}
