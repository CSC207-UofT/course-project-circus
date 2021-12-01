package warehouse.logistics.orders;

import warehouse.inventory.Item;
import warehouse.transactions.Distributable;

/**
 * An Order to place an Item into an available StorageUnit in the Warehouse.
 */
public class PlaceOrder extends Order {
    private final Distributable source;
    private final Item item;

    /**
     * Construct an Order given a source and Item to move.
     * @param source The source Distributable to move the item from.
     * @param item The Item to move.
     */
    public PlaceOrder(Distributable source, Item item) {
        this.source = source;
        this.item = item;
    }

    public Distributable getSource() {
        return source;
    }

    public Item getItem() {
        return item;
    }

    @Override
    public boolean isReady() {
        return false;
    }
}
