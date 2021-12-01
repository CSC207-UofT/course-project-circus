package warehouse.transactions;

import warehouse.inventory.Item;

import java.util.UUID;

/**
 * An Order specifying that an Item should be moved from a Receivable to Distributable.
 */
public class Order {
    private final UUID id;
    private final Distributable source;
    private final Receivable destination;
    private final Item item;

    /**
     * Construct an Order given a source, destination, and Item to move.
     * @param source The source Distributable to move the item from.
     * @param destination The destination Receivable to move the item to.
     * @param item The Item to move.
     */
    public Order(Distributable source, Receivable destination, Item item) {
        id = UUID.randomUUID();
        this.source = source;
        this.destination = destination;
        this.item = item;
    }

    public UUID getId() {
        return id;
    }

    public Distributable getSource() {
        return source;
    }

    public Receivable getDestination() {
        return destination;
    }

    public Item getItem() {
        return item;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", source=" + source +
                ", destination=" + destination +
                ", item=" + item +
                '}';
    }
}
