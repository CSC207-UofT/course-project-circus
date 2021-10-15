package circus.warehouse;


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
     * Constructs an instance of the WareHouseController
     */
    public WarehouseController(Warehouse warehouse)
    {
        this.warehouse = warehouse;
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
}
