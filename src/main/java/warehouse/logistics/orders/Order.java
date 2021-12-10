package warehouse.logistics.orders;

import messaging.Message;
import utils.RandomUtils;
import warehouse.robots.Robot;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The base Order class. Represents a command to do "work" in the WarehouseLayout.
 */
public abstract class Order {
    private final String id;
    private final Date createdAt;
    private OrderStatus status;
    private Robot handler;

    private final Message<Order> onAssigned;
    private final Message<Order> onComplete;

    /**
     * Construct an Order with a random UUID.
     */
    public Order() {
        id = RandomUtils.randomId();
        createdAt = new Date(System.currentTimeMillis());
        handler = null;
        status = OrderStatus.PENDING;

        onAssigned = new Message<>();
        onComplete = new Message<>();
    }

    /**
     * Return whether this Order is ready to be processed.
     * @return True if the Order is ready, and False otherwise.
     */
    public abstract boolean isReady();

    /**
     * Assign the given Robot to this order.
     * @param robot The Robot to assign this order to. If this Order is already assigned to a Robot, then that Robot
     *              will be unassigned from this Order first.
     */
    public void assign(Robot robot) {
        // Unassigned order if handler is not null
        if (handler != null) {
            handler.setOrder(null);
        }
        // Assign order
        handler = robot;
        handler.setOrder(this);
        status = OrderStatus.ASSIGNED;
        onAssigned.execute(this);
    }

    /**
     * Mark this Order as in progress.
     */
    public void setInProgress() {
        status = OrderStatus.IN_PROGRESS;
    }

    /**
     * Mark this Order as complete.
     */
    public void setComplete() {
        status = OrderStatus.COMPLETE;
        if (handler != null) {
            handler.setOrder(null);
            handler = null;
        }
        onComplete.execute(this);
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

    public Message<Order> getOnAssigned() {
        return onAssigned;
    }

    public Message<Order> getOnComplete() {
        return onComplete;
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
