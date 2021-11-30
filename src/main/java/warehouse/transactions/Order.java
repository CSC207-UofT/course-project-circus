package warehouse.transactions;

import warehouse.inventory.Item;

/**
 * An Order specifying that an Item should be moved from a Receivable to Distributable.
 */
public class Order {
    private final Distributable source;
    private final Receivable destination;
    private final Item item;

    /**
     * Construct an Order given a source, destination, and Item to move.
     * @param source The source
     * @param destination
     * @param item
     */
    public Order(Distributable source, Receivable destination, Item item) {
        this.source = source;
        this.destination = destination;
        this.item = item;
    }
}
