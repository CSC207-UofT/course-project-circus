package warehouse.logistics.orders;

import utils.RandomUtils;
import warehouse.transactions.Distributable;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The base Order class. Represents a command to do "work" in the Warehouse.
 */
public abstract class Order {
    private final String id;
    private final Date createdAt;

    /**
     * Construct an Order with a random UUID.
     */
    public Order() {
        id = RandomUtils.randomId();
        createdAt = new Date(System.currentTimeMillis());
    }

    /**
     * Return whether this Order is ready to be processed.
     * @return True if the Order is ready, and False otherwise.
     */
    public abstract boolean isReady();

    /**
     * Return the source of this Order.
     * @return Where the order is originating from (Distributable).
     */
    public abstract Distributable getSource();

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

    @Override
    public String toString() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        return "Order{" +
                "id=" + id +
                ", createdAt=" + dateFormatter.format(createdAt) +
                '}';
    }
}
