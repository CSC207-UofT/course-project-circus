package warehouse;


import warehouse.inventory.PartCatalogue;
import warehouse.inventory.Item;
import warehouse.transactions.Order;

/**
 * Controls the Warehouse.
 */
public class WarehouseController {
    /**
     * The Warehouse to manage.
     */
    private final Warehouse warehouse;
    /**
     * Available items serviced by the Warehouse.
     */
    private final PartCatalogue partCatalogue;

    /**
     * Constructs an instance of the WarehouseController.
     */
    public WarehouseController(Warehouse warehouse, PartCatalogue partCatalogue)
    {
        this.warehouse = warehouse;
        this.partCatalogue = partCatalogue;
    }

    /**
     * Insert an Item fromm the outside world. This will place the Item into an available ReceiveDepot, and then issue
     * an Order for the Item to be moved from that ReceiveDepot to an available StorageUnit in the Warehouse.
     * @param item The Item to insert.
     * @return an Order representing a request to move the Item to an available StorageUnit in the Warehouse.
     */
    public Order insertItem(Item item) {
        // TODO: Implement me!
        return null;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public PartCatalogue getPartCatalogue() {
        return partCatalogue;
    }
}