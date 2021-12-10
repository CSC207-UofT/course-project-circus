package warehouse.logistics.orders;

import warehouse.WarehouseLayout;
import warehouse.inventory.Item;
import warehouse.logistics.assignment.StorageTileAssignmentPolicy;
import warehouse.tiles.Rack;
import warehouse.tiles.Tile;
import warehouse.transactions.Distributable;

import java.util.ArrayList;
import java.util.List;

/**
 * An Order to place an Item into an available Rack in the WarehouseLayout.
 */
public class PlaceOrder extends NavigateOrder {
    private final Distributable source;
    private final Item item;
    private final WarehouseLayout<?> layout;
    private final StorageTileAssignmentPolicy<Rack> rackAssignmentPolicy;

    /**
     * Construct an Order given a source and Item to move.
     * @param source The source Distributable to move the item from.
     * @param item The Item to move.
     * @param rackAssignmentPolicy The policy to use for assigning items to a Rack.
     */
    public PlaceOrder(Distributable source, Item item, WarehouseLayout<?> layout,
                      StorageTileAssignmentPolicy<Rack> rackAssignmentPolicy) {
        super(new ArrayList<>());
        this.source = source;
        this.item = item;
        this.layout = layout;
        this.rackAssignmentPolicy = rackAssignmentPolicy;

        getOnAssigned().addListener(this::onAssigned);
        this.waypoints.add(getFirstEmptyNeighbour(source.getTile()));
    }

    /**
     * Called when this Order is assigned to a Robot.
     */
    private void onAssigned(Order order) {
        waypoints.clear();
        this.waypoints.add(getFirstEmptyNeighbour(source.getTile()));
        Rack rack = rackAssignmentPolicy.assign(layout, item);
        waypoints.add(getFirstEmptyNeighbour(rack));
    }

    public Distributable getSource() {
        return source;
    }

    public Item getItem() {
        return item;
    }

    /**
     * Return whether this order is ready to be processed. This order can be processed if and only if there exists
     * a Rack in the WarehouseLayout that can store the Item associated with this PlaceOrder.
     * @return True if this PlaceOrder is ready, and False otherwise.
     */
    @Override
    public boolean isReady() {
        return rackAssignmentPolicy.isAssignable(layout, item);
    }

    /**
     * Get the first empty neighbour of the given Tile.
     */
    private Tile getFirstEmptyNeighbour(Tile tile) {
        List<Integer> neighbours = layout.getCoordinateSystem().getNeighbours(tile.getIndex());
        for (int index : neighbours) {
            if (index == -1) continue;
            Tile neighbourTile = layout.getTileAt(index);
            if (layout.isEmpty(neighbourTile)) {
                return neighbourTile;
            }
        }
        return null;
    }
}
