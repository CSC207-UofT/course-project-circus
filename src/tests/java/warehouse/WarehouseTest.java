package warehouse;

import org.junit.jupiter.api.Test;
import warehouse.geometry.grid.GridWarehouseCoordinateSystem;
import warehouse.geometry.grid.Point;
import warehouse.inventory.Item;
import warehouse.inventory.Part;
import warehouse.inventory.PartCatalogue;
import warehouse.logistics.orders.OrderQueue;
import warehouse.logistics.orders.PlaceOrder;
import warehouse.robots.Robot;
import warehouse.robots.RobotMapper;
import warehouse.tiles.ReceiveDepot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class WarehouseTest {
    @Test
    public void testReceiveItem1() {
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
        // Add parts to the part catalogue
        Part part = new Part("Cucumber", "A vegetable");
        warehouse.getState().getPartCatalogue().addPart(part);
        // Add a robot at (0, 0)
        Robot robot = new Robot(null);
        warehouse.getState().getRobotMapper().addRobotAt(robot, new Point(0, 0));
        // Try receiving an item into the warehouse. Since we have no receive depots, the returned order should be null!
        assertNull(warehouse.receiveItem(new Item(part)));
    }

    @Test
    public void testReceiveItem2() {
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
        // Add a receive depot
        ReceiveDepot receiveDepot = new ReceiveDepot(); // Create a new receive depot
        warehouse.getState().getLayout().setTileAt(new Point(5, 5), receiveDepot); // Associate it with the coordinate (5,5)
        // Add parts to the part catalogue
        Part part = new Part("Cucumber", "A vegetable");
        warehouse.getState().getPartCatalogue().addPart(part);
        // Add a robot at (0, 0)
        Robot robot = new Robot(null);
        warehouse.getState().getRobotMapper().addRobotAt(robot, new Point(0, 0));
        // Try receiving an item into the warehouse
        PlaceOrder order = warehouse.receiveItem(new Item(part));
        // The source of the order should be the ReceiveDepot at (5, 5)
        assertEquals(order.getSource(), receiveDepot);
    }

    @Test
    public void testReceiveItem3() {
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
        // Add a receive depot
        ReceiveDepot receiveDepot1 = new ReceiveDepot(-1, 1); // Create a new ReceiveDepot with a capacity of 1
        ReceiveDepot receiveDepot2 = new ReceiveDepot(); // Create a new ReceiveDepot with infinite capacity
        warehouse.getState().getLayout().setTileAt(new Point(5, 5), receiveDepot1); // Associate it with the coordinate (5,5)
        warehouse.getState().getLayout().setTileAt(new Point(6, 6), receiveDepot2); // Associate it with the coordinate (6,6)
        // Add parts to the part catalogue
        Part part = new Part("Cucumber", "A vegetable");
        warehouse.getState().getPartCatalogue().addPart(part);
        // Add a robot at (0, 0)
        Robot robot = new Robot(null);
        warehouse.getState().getRobotMapper().addRobotAt(robot, new Point(0, 0));
        // Try receiving an item into the warehouse
        PlaceOrder order1 = warehouse.receiveItem(new Item(part));
        // The source of the order should be the ReceiveDepot at (5, 5)
        assertEquals(order1.getSource(), receiveDepot1);
        // Now, receiveDepot1 is full, so the next item we receive should go to receiveDepot2
        PlaceOrder order2 = warehouse.receiveItem(new Item(part));
        assertEquals(order2.getSource(), receiveDepot2);
    }
}
