package warehouse;

import warehouse.inventory.PartCatalogue;
import warehouse.inventory.Item;
import warehouse.logistics.assignment.BasicRackAssignmentPolicy;
import warehouse.logistics.assignment.BasicReceiveDepotAssignmentPolicy;
import warehouse.logistics.assignment.BasicShipDepotAssignmentPolicy;
import warehouse.logistics.assignment.StorageTileAssignmentPolicy;
import warehouse.logistics.orders.OrderQueue;
import warehouse.logistics.orders.PlaceOrder;
import warehouse.tiles.Rack;
import warehouse.tiles.ReceiveDepot;
import warehouse.tiles.ShipDepot;

/**
 * Controls the Warehouse.
 */
public class WarehouseController {
    private final Warehouse warehouse;
    private final PartCatalogue partCatalogue;

    private final StorageTileAssignmentPolicy<ReceiveDepot> receiveDepotAssignmentPolicy;
    private final StorageTileAssignmentPolicy<ShipDepot> shipDepotAssignmentPolicy;
    private final StorageTileAssignmentPolicy<Rack> rackAssignmentPolicy;

    private final OrderQueue orderQueue;

    /**
     * Construct a WarehouseController.
     * @param warehouse The Warehouse to manage.
     * @param partCatalogue Available parts serviced by the Warehouse.
     * @param receiveDepotAssignmentPolicy The policy for assigning incoming items to a ReceiveDepot.
     * @param shipDepotAssignmentPolicy The policy for assigning outgoing items to a ShipDepot.
     * @param rackAssignmentPolicy The policy for assigning items to a Rack.
     */
    public WarehouseController(Warehouse warehouse, PartCatalogue partCatalogue,
                               StorageTileAssignmentPolicy<ReceiveDepot> receiveDepotAssignmentPolicy,
                               StorageTileAssignmentPolicy<ShipDepot> shipDepotAssignmentPolicy,
                               StorageTileAssignmentPolicy<Rack> rackAssignmentPolicy)
    {
        this.warehouse = warehouse;
        this.partCatalogue = partCatalogue;
        // Assignment policies
        this.receiveDepotAssignmentPolicy = receiveDepotAssignmentPolicy;
        this.shipDepotAssignmentPolicy = shipDepotAssignmentPolicy;
        this.rackAssignmentPolicy = rackAssignmentPolicy;
        // Initialise order queue
        orderQueue = new OrderQueue();
    }

    /**
     * Construct a WarehouseController with basic assignment policies.
     * @param warehouse The Warehouse to manage.
     * @param partCatalogue Available parts serviced by the Warehouse.
     */
    public WarehouseController(Warehouse warehouse, PartCatalogue partCatalogue) {
        this(warehouse, partCatalogue,
                new BasicReceiveDepotAssignmentPolicy(warehouse),
                new BasicShipDepotAssignmentPolicy(warehouse),
                new BasicRackAssignmentPolicy(warehouse));
    }

    /**
     * Receive an Item from the outside world. This will place the Item into an available ReceiveDepot, and then issue
     * an Order for the Item to be moved from that ReceiveDepot to an available StorageUnit in the Warehouse.
     * @param item The Item to insert.
     * @return a PlaceOrder representing a request to move the Item to an available StorageUnit in the Warehouse,
     * or null if the Item cannot be inserted into the Warehouse.
     */
    public PlaceOrder receiveItem(Item item) {
        ReceiveDepot receiveDepot = receiveDepotAssignmentPolicy.assign(item);
        if (receiveDepot == null) {
            // Item could not be assigned to a ReceiveDepot (source)!
            return null;
        } else {
            PlaceOrder order = new PlaceOrder(receiveDepot, item, rackAssignmentPolicy);
            orderQueue.add(order);
            return order;
        }
    }

    /**
     * Getter Method for warehouse
     * @return warehouse
     */
    public Warehouse getWarehouse() {
        return warehouse;
    }

    /**
     * Getter method for Part Catalogue
     * @return partCatalogue
     */
    public PartCatalogue getPartCatalogue() {
        return partCatalogue;
    }
}