package warehouse.logistics.orders;

import warehouse.inventory.Item;
import warehouse.transactions.Distributable;
import warehouse.transactions.Receivable;

/**
 * An Order to place an Item into an available StorageUnit in the Warehouse.
 */
public class ReceiveOrder {
    private final Distributable source;
    private final Item item;
}
