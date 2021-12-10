package warehouse;

import warehouse.geometry.WarehouseCoordinate;
import warehouse.geometry.WarehouseCoordinateSystem;
import warehouse.inventory.Item;
import warehouse.logistics.assignment.BasicRackAssignmentPolicy;
import warehouse.logistics.assignment.BasicReceiveDepotAssignmentPolicy;
import warehouse.logistics.assignment.BasicShipDepotAssignmentPolicy;
import warehouse.logistics.assignment.StorageTileAssignmentPolicy;
import warehouse.logistics.orders.OrderMatcher;
import warehouse.logistics.orders.PlaceOrder;
import warehouse.tiles.Rack;
import warehouse.tiles.ReceiveDepot;
import warehouse.tiles.ShipDepot;

/**
 * The main level controller for the Warehouse.
 */
public class Warehouse<T extends WarehouseCoordinateSystem<U>, U extends WarehouseCoordinate> {
    private final WarehouseState<T, U> state;
    private final OrderMatcher orderMatcher;

    private final StorageTileAssignmentPolicy<ReceiveDepot> receiveDepotAssignmentPolicy;
    private final StorageTileAssignmentPolicy<ShipDepot> shipDepotAssignmentPolicy;
    private final StorageTileAssignmentPolicy<Rack> rackAssignmentPolicy;

    /**
     * Construct a Warehouse.
     * @param state The warehouse state.
     * @param receiveDepotAssignmentPolicy The policy for assigning incoming items to a ReceiveDepot.
     * @param shipDepotAssignmentPolicy The policy for assigning outgoing items to a ShipDepot.
     * @param rackAssignmentPolicy The policy for assigning items to a Rack.
     */
    public Warehouse(WarehouseState<T, U> state,
                     StorageTileAssignmentPolicy<ReceiveDepot> receiveDepotAssignmentPolicy,
                     StorageTileAssignmentPolicy<ShipDepot> shipDepotAssignmentPolicy,
                     StorageTileAssignmentPolicy<Rack> rackAssignmentPolicy)
    {
        this.state = state;
        this.orderMatcher = new OrderMatcher(state.getOrderQueue(), state.getRobotMapper());
        // Assignment policies
        this.receiveDepotAssignmentPolicy = receiveDepotAssignmentPolicy;
        this.shipDepotAssignmentPolicy = shipDepotAssignmentPolicy;
        this.rackAssignmentPolicy = rackAssignmentPolicy;
    }

    /**
     * Construct a Warehouse with basic assignment policies.
     * @param state The warehouse state to manage.
     */
    public Warehouse(WarehouseState<T, U> state) {
        this(state,
                new BasicReceiveDepotAssignmentPolicy(),
                new BasicShipDepotAssignmentPolicy(),
                new BasicRackAssignmentPolicy());
    }

    /**
     * Update the Warehouse for this timestep.
     */
    public void update() {
        orderMatcher.match();
    }

    /**
     * Receive an Item from the outside world. This will place the Item into an available ReceiveDepot, and then issue
     * an Order for the Item to be moved from that ReceiveDepot to an available StorageUnit in the WarehouseLayout.
     * @param item The Item to insert.
     * @return a PlaceOrder representing a request to move the Item to an available StorageUnit in the WarehouseLayout,
     * or null if the Item cannot be inserted into the WarehouseLayout.
     */
    public PlaceOrder receiveItem(Item item) {
        ReceiveDepot receiveDepot = receiveDepotAssignmentPolicy.assign(state.getLayout(), item);
        if (receiveDepot == null) {
            // Item could not be assigned to a ReceiveDepot (source)!
            return null;
        } else {
            receiveDepot.getStorageUnit().addItem(item);
            PlaceOrder order = new PlaceOrder(receiveDepot, item, state.getLayout(), rackAssignmentPolicy);
            state.getOrderQueue().add(order);
            return order;
        }
    }

    /**
     * Get the warehouse state for this Warehouse.
     */
    public WarehouseState<T, U> getState() {
        return state;
    }
}