package circus.warehouse;


import circus.inventory.InventoryCatalogue;
import circus.inventory.Item;

/**
 * This class will be in charge of the WareHouse as a whole and will be the class that the User interacts with
 * rather than other more basic classes.
 */
public class WarehouseController {
    /**
     * The Warehouse to manage.
     */
    private final Warehouse warehouse;
    /**
     * Available items serviced by the Warehouse.
     */
    private final InventoryCatalogue inventoryCatalogue;

    /**
     * Constructs an instance of the WarehouseController.
     */
    public WarehouseController(Warehouse warehouse, InventoryCatalogue inventoryCatalogue)
    {
        this.warehouse = warehouse;
        this.inventoryCatalogue = inventoryCatalogue;
    }

    /**
     * Insert an Item into the Warehouse into an available Rack.
     * @param item The Item to insert.
     * @return True if the Item could be inserted, and False otherwise.
     */
    public boolean insertItem(Item item) {
        Tile tile = warehouse.findRackFor(item);
        if (tile == null) {
            // Can't insert this item!
            return false;
        } else {
            tile.getStorageUnit().addItem(item);
            return true;
        }
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public InventoryCatalogue getInventoryCatalogue() {
        return inventoryCatalogue;
    }
}
