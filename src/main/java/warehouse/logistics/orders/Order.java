package warehouse.logistics.orders;

import utils.RandomUtils;
import warehouse.robots.Robot;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The base Order class. Represents a command to do "work" in the Warehouse.
 */
public abstract class Order {
    private final String id;
    private final Date createdAt;
    private OrderStatus status;
    private Robot handler;

    /**
     * Construct an Order with a random UUID.
     */
    public Order() {
        id = RandomUtils.randomId();
        createdAt = new Date(System.currentTimeMillis());
        handler = null;
        status = OrderStatus.PENDING;
    }

    /**
     * Return whether this Order is ready to be processed.
     * @return True if the Order is ready, and False otherwise.
     */
    public abstract boolean isReady();

    /**
     * Assign the given Robot to this order.
     * @param robot The Robot to assign this order to.
     */
    public void assignTo(Robot robot) {
        handler = robot;
        status = OrderStatus.ASSIGNED;
    }

    /**
     * Retrieve the id of this order.
     */
    public String getId() {
        return id;
    }

    /**
     * Get the time this order was created at.
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Get the Robot handling this Order.
     */
    public Robot getHandler() {
        return handler;
    }

    public OrderStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        return "Order{" +
                "id=" + id +
                ", createdAt=" + dateFormatter.format(createdAt) +
                '}';
    }
}
