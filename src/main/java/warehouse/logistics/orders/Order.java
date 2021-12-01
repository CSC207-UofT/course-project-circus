package warehouse.logistics.orders;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * The base Order class. Represents a command to do "work" in the Warehouse.
 */
public abstract class Order {
    private final UUID id;
    private final Date createdAt;

    /**
     * Construct an Order with a random UUID.
     */
    public Order() {
        id = UUID.randomUUID();
        createdAt = new Date(System.currentTimeMillis());
    }

    /**
     * Return whether this Order is ready to be processed.
     * @return True if the Order is ready, and False otherwise.
     */
    public abstract boolean isReady();

    public UUID getId() {
        return id;
    }

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
