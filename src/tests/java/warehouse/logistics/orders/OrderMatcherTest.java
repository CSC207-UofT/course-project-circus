package warehouse.logistics.orders;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import warehouse.Warehouse;
import warehouse.WarehouseLayout;
import warehouse.WarehouseState;
import warehouse.geometry.grid.GridWarehouseCoordinateSystem;
import warehouse.geometry.grid.Point;
import warehouse.inventory.Item;
import warehouse.inventory.Part;
import warehouse.inventory.PartCatalogue;
import warehouse.logistics.assignment.BasicRackAssignmentPolicy;
import warehouse.robots.Robot;
import warehouse.robots.RobotMapper;
import warehouse.tiles.Rack;
import warehouse.tiles.ReceiveDepot;

import static org.junit.jupiter.api.Assertions.*;

public class OrderMatcherTest {
    private static Warehouse<GridWarehouseCoordinateSystem, Point> warehouse;
    private static OrderMatcher orderMatcher;

    private static Order order1;
    private static Order order2;
    private static CustomOrder order3;

    private static ReceiveDepot receiveDepot;
    private static Rack rack;

    @BeforeAll
    static void beforeAll() {
        // Create an empty 10x10 warehouse
        GridWarehouseCoordinateSystem coordinateSystem = new GridWarehouseCoordinateSystem(10, 10);
        warehouse = new Warehouse<>(new WarehouseState<>(
                new PartCatalogue(),
                coordinateSystem,
                new WarehouseLayout<>(coordinateSystem),
                new RobotMapper<>(coordinateSystem),
                new OrderQueue()
        ));
        // Create order matcher
        orderMatcher = new OrderMatcher(warehouse.getState().getOrderQueue(), warehouse.getState().getRobotMapper());
        // Add receive depot and rack
        WarehouseLayout<Point> layout = warehouse.getState().getLayout();
        receiveDepot = new ReceiveDepot();
        rack = new Rack();
        layout.setTileAt(new Point(0, 0), rack);
        layout.setTileAt(new Point(5, 5), receiveDepot);
        // Create orders
        try {
            order1 = new Order() {
                @Override
                public boolean isReady() {
                    return true;
                }
            };
            Thread.sleep(1);

            Part part = new Part("Example Part", "No description.");
            order2 = new PlaceOrder(receiveDepot, new Item(part),
                    layout, new BasicRackAssignmentPolicy());
            Thread.sleep(1);

            order3 = new CustomOrder();
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void clearAndAddOrders() {
        OrderQueue orderQueue = warehouse.getState().getOrderQueue();
        orderQueue.clear();
        orderQueue.add(order1);
        orderQueue.add(order2);
        orderQueue.add(order3);
    }

    /**
     * Test the OrderMatcher when there are no Robots (at all!)
     */
    @Test
    @org.junit.jupiter.api.Order(1)
    public void testNoRobots() {
        // Add orders
        clearAndAddOrders();
        // Match
        orderMatcher.match();
        // There are no robots so the order queue shouldn't have been mutated!
        assertEquals(3, warehouse.getState().getOrderQueue().size());
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    /*
     * Test the OrderMatcher when there are Robots but none are available.
     */
    public void testRobotsNotAvailable() {
        // Add orders
        clearAndAddOrders();
        // Add robots
        RobotMapper<Point> robotMapper = warehouse.getState().getRobotMapper();
        robotMapper.removeAllRobots();
        Robot robot = new Robot();
        robotMapper.addRobotAt(robot, new Point(0, 0));
        new CustomOrder().assign(robot); // Make the robot busy
        // Match
        orderMatcher.match();
        // There are no robots so the order queue shouldn't have been mutated!
        assertEquals(3, warehouse.getState().getOrderQueue().size());
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    /*
     * Test the OrderMatcher when there are available Robots.
     */
    public void testRobotsAvailable() {
        // Add orders
        clearAndAddOrders();
        // Add robots
        RobotMapper<Point> robotMapper = warehouse.getState().getRobotMapper();
        robotMapper.removeAllRobots();
        Robot robot1 = new Robot();
        robotMapper.addRobotAt(robot1, new Point(0, 0));
        // Match
        orderMatcher.match();
        // order1 was created first, and it is ready, so robot1 should be matched with order1
        assertEquals(2, warehouse.getState().getOrderQueue().size());
        assertEquals(order1, robot1.getOrder());
        // Now try matching again...nothing should change since no robot is available
        orderMatcher.match();
        assertEquals(2, warehouse.getState().getOrderQueue().size());
        assertEquals(order1, robot1.getOrder()); // Make sure the robot wasn't reassigned...that'd be bad!
        // Let's make a new robot
        Robot robot2 = new Robot();
        robotMapper.addRobotAt(robot2, new Point(0, 0));
        // Match
        orderMatcher.match();
        // order2 was created second, and it is ready, so robot2 should be matched with order2
        assertEquals(1, warehouse.getState().getOrderQueue().size());
        assertEquals(order2, robot2.getOrder());
        // Let's make robot1 free
        robot1.setOrder(null);
        // Now, let's match again
        orderMatcher.match();
        // robot1 is free but order3 is not ready, so nothing should change.
        assertFalse(robot1.getIsBusy());
        assertEquals(1, warehouse.getState().getOrderQueue().size());
        // Let's make order3 ready
        order3.setReady(true);
        // robot1 is free and there is now a single READY order in the queue: order3
        // So, robot1 should be matched with order3, and the queue should be empty
        orderMatcher.match();
        assertTrue(warehouse.getState().getOrderQueue().isEmpty());
        assertEquals(order3, robot1.getOrder());
    }
}
