package application.shell.commands;

import application.shell.ShellApplication;
import application.shell.commands.CreatePartCommand;
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

public class CreatePartCommandTest {
    @Test
    public void testCreatePartCommand() {
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
        // Create shell application and command
        ShellApplication<GridWarehouseCoordinateSystem, Point> application = new ShellApplication<>(
                warehouse, null, null, null);

        CreatePartCommand<GridWarehouseCoordinateSystem, Point> command = new CreatePartCommand<>();
        ShellCommandExecutor commandExecutor = new ShellCommandExecutor(application, new ShellCommand[]{command});
        // Create part and execute command
        Part part1 = new Part("mango_fruit_1234", "Mango", "A yummy fruit");
        commandExecutor.execute(String.format("create-part '%s' '%s' '%s'", part1.getId(), part1.getName(),
                part1.getDescription()));
        // Now the part catalogue should have one part in it!
        PartCatalogue partCatalogue = warehouse.getState().getPartCatalogue();
        assertEquals(1, partCatalogue.getParts().size());
        assertEquals(part1, partCatalogue.getPartById("mango_fruit_1234"));

        // Test part command when a part of the same id already exists!
        Part part2 = new Part(part1.getId(), "Rotten mango", "A not so yummy fruit");
        String output = commandExecutor.execute(String.format("create-part '%s' '%s' '%s'", part2.getId(),
                part2.getName(), part2.getDescription()));
        assertEquals("Error - Part with same id already exists!", output);
    }
}
