package warehouse.logistics.orders;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import warehouse.Warehouse;
import warehouse.inventory.Item;
import warehouse.inventory.Part;
import warehouse.logistics.assignment.BasicRackAssignmentPolicy;

import static org.junit.jupiter.api.Assertions.*;

/**
 * A custom order used for testing.
 */
class CustomOrder extends Order {
    private boolean isReady = false;

    /**
     * Sets the "readiness" of this Order. This is used in the getNextOrder test to simulate the readiness
     * of an Order changing dynamically.
     */
    public void setReady(boolean isReady) {
        this.isReady = isReady;
    }

    @Override
    public boolean isReady() {
        return isReady;
    }
};

/**
 * Test the OrderQueue.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderQueueTest {
    private static OrderQueue orderQueue;
    private static Order order1;
    private static Order order2;
    private static CustomOrder order3;

    @BeforeAll
    static void beforeAll() {
        orderQueue = new OrderQueue();
        Part part = new Part("Example Part", "No description.");
        // FIXME: Sleep in between instantiating orders to make sure they have different creation dates.
        try {
            order1 = new Order() {
                @Override
                public boolean isReady() {
                    return true;
                }
            };
            Thread.sleep(1);
            order2 = new PlaceOrder(null, new Item(part),
                    new BasicRackAssignmentPolicy(new Warehouse(1, 1)));
            Thread.sleep(1);
            order3 = new CustomOrder();
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test adding to an OrderQueue.
     */
    @Test
    @org.junit.jupiter.api.Order(1)
    void testAddOrder() {
        orderQueue.add(order1); // Oldest order
        orderQueue.add(order2);
        orderQueue.add(order3); // Newest order
        assertEquals(3, orderQueue.size());
    }

    /**
     * Test getting the next order in the OrderQueue.
     */
    @Test
    @org.junit.jupiter.api.Order(2)
    void testGetNextOrder() {
        // The order queue currently contains three orders, in order of creation date:
        // 1. a generic Order that is READY, (order1)
        // 2. a PlaceOrder that is NOT ready, (order2)
        // 3. a generic Order that is NOT READY. (order3)
        //
        assertEquals(order1, orderQueue.getNextOrder());
        // Now, there are no ready orders in the queue, so we should get null.
        // The queue looks like:
        // 1. a PlaceOrder that is NOT ready, (order2)
        // 2. a generic Order that is NOT READY. (order3)
        assertNull(orderQueue.getNextOrder());
        // Let's set order3 to be ready
        order3.setReady(true);
        // Now, even though order3 is NEWER than order2, we should get order3 as our next order.
        assertEquals(order3, orderQueue.getNextOrder());
        // Again, there are no more ready orders in the queue, as it consists of a single order:
        // a PlaceOrder that is NOT ready, (order2). So, we should get null.
        assertNull(orderQueue.getNextOrder());
    }
}
