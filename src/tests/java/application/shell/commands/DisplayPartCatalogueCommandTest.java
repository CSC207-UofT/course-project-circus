package application.shell.commands;

import application.shell.ShellApplication;
import application.shell.commands.framework.ShellCommand;
import application.shell.commands.framework.ShellCommandExecutor;
import org.junit.jupiter.api.Test;
import warehouse.Warehouse;
import warehouse.WarehouseLayout;
import warehouse.WarehouseState;
import warehouse.geometry.grid.GridWarehouseCoordinateSystem;
import warehouse.geometry.grid.Point;
import warehouse.inventory.Part;
import warehouse.inventory.PartCatalogue;
import warehouse.logistics.orders.OrderQueue;
import warehouse.robots.RobotMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DisplayPartCatalogueCommandTest {
    @Test
    public void testDisplayPartCatalogueCommand() {
        // Create an empty 10x10 warehouse
        GridWarehouseCoordinateSystem coordinateSystem = new GridWarehouseCoordinateSystem(10, 10);
        Warehouse<GridWarehouseCoordinateSystem, Point> warehouse = new Warehouse<>(new WarehouseState<>(
                new PartCatalogue(),
                coordinateSystem,
                new WarehouseLayout<>(coordinateSystem),
                new RobotMapper<>(coordinateSystem),
                null,
                new OrderQueue()
        ));
        PartCatalogue partCatalogue = warehouse.getState().getPartCatalogue();
        partCatalogue.addPart(new Part("1","Normal Mango", "A yummy fruit."));
        partCatalogue.addPart(new Part("2","Ripe Mango", "A really yummy fruit."));
        partCatalogue.addPart(new Part("3","Rotten Mango", "A not so yummy fruit."));
        partCatalogue.addPart(new Part("4","iPhone", ""));

        // Create shell application and command
        ShellApplication<GridWarehouseCoordinateSystem, Point> application = new ShellApplication<>(
                warehouse, null, null, null);

        DisplayPartCatalogue<GridWarehouseCoordinateSystem, Point> command = new DisplayPartCatalogue<>();
        ShellCommandExecutor commandExecutor = new ShellCommandExecutor(application, new ShellCommand[]{command});
        String output = commandExecutor.execute("display-parts");
        String expected = String.format("(4) parts in the part catalogue\n" +
                                        "- %s\n" +
                                        "- %s\n" +
                                        "- %s\n" +
                                        "- %s\n", partCatalogue.getPartById("1"), partCatalogue.getPartById("2"),
                partCatalogue.getPartById("3"), partCatalogue.getPartById("4"));
        assertEquals(expected.strip(), output.strip());
    }
}
