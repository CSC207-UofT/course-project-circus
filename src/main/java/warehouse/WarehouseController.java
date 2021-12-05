package warehouse;


import warehouse.inventory.PartCatalogue;
import warehouse.inventory.Item;
import warehouse.tiles.Tile;
import warehouse.transactions.Order;
import warehouse.storage.ReceiveDepot;
import warehouse.transactions.Receivable;

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
     *
     * @param item The item to insert
     * @param intermediate The depot to keep this Item
     * @return The Order for this Item.
     */
    public Order insertItem(Item item, Receivable intermediate) {
        // TODO: Implement me!
        return null;
    }

    /**
     * Receive an Item.
     * @param item The item to receive.
     * @return a ReceiveDepot that can received the given Item, or null if the Item can't be received.
     */
    public ReceiveDepot receiveItem(Item item) {
        // TODO: Implement me
        return null;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public PartCatalogue getPartCatalogue() {
        return partCatalogue;
    }

    public static boolean IsWalkable(Warehouse map, Tile tile) {
        if (tile.getY() < 0 || tile.getY() > map.getHeight() - 1) return false;
        if (tile.getX() < 0 || tile.getX() > map.getWidth() - 1) return false;
        return map.getTileAt(tile.getX(), tile.getY()).isEmpty();
}