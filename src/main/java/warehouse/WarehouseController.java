package main.java.warehouse;


import main.java.inventory.Item;
import main.java.inventory.InventoryCatalogue;

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
     * @return the Tile containing the StorageUnit that the item was inserted into, or null if it could not be inserted.
     */
    public Tile insertItem(Item item) {
        Tile tile = warehouse.findRackFor(item);
        if (tile == null) {
            // Can't insert this item!
            return null;
        } else {
            tile.getStorageUnit().addItem(item);
            return tile;
        }
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public InventoryCatalogue getInventoryCatalogue() {
        return inventoryCatalogue;
    }
}
