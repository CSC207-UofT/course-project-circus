package warehouse.logistics.orders;

import warehouse.inventory.Item;
import warehouse.logistics.assignment.StorageTileAssignmentPolicy;
import warehouse.tiles.Rack;
import warehouse.transactions.Distributable;

/**
 * An Order to place an Item into an available Rack in the Warehouse.
 */
public class PlaceOrder extends Order {
    private final Distributable source;
    private final Item item;
    private final StorageTileAssignmentPolicy<Rack> rackAssignmentPolicy;

    /**
     * Construct an Order given a source and Item to move.
     * @param source The source Distributable to move the item from.
     * @param item The Item to move.
     * @param rackAssignmentPolicy The policy to use for assigning items to a Rack.
     */
    public PlaceOrder(Distributable source, Item item, StorageTileAssignmentPolicy<Rack> rackAssignmentPolicy) {
        this.source = source;
        this.item = item;
        this.rackAssignmentPolicy = rackAssignmentPolicy;
    }

    public Distributable getSource() {
        return source;
    }

    public Item getItem() {
        return item;
    }

    /**
     * Return whether this order is ready to be processed. This order can be processed if and only if there exists
     * a Rack in the Warehouse that can store the Item associated with this PlaceOrder.
     * @return True if this PlaceOrder is ready, and False otherwise.
     */
    @Override
    public boolean isReady() {
        return rackAssignmentPolicy.isAssignable(item);
    }
}
