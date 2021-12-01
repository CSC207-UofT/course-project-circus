package warehouse.logistics.orders;

import warehouse.inventory.Item;
import warehouse.transactions.Distributable;
import warehouse.transactions.Receivable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * An Order specifying that an Item should be moved from a Receivable to Distributable.
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
