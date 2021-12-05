package warehouse;


import concretePathfinding.TileNode;
import org.lwjgl.system.CallbackI;
import warehouse.inventory.PartCatalogue;
import warehouse.inventory.Item;
import warehouse.orders.Order;
import warehouse.orders.OrderQueue;

import java.lang.reflect.Array;
import java.util.*;

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
     * Warehouse's current order queue.
     */
    private final OrderQueue orderQueue;

    /**
     * Constructs an instance of the WarehouseController.
     */
    public WarehouseController(Warehouse warehouse, PartCatalogue partCatalogue)
    {
        this.warehouse = warehouse;
        this.partCatalogue = partCatalogue;
        this.orderQueue = new OrderQueue();
    }

    //TODO: Update once Robot functionality is completed.
    /**
     * Insert an Item into the Warehouse into an available Rack - first add to order queue + update status once
     * item is successfully added.
     * @param item The Item to insert.
     * @return the Tile containing the StorageUnit that the item was inserted into, or null if it could not be inserted.
     */
    public Tile insertItem(Item item) {
        Order order = new Order(item);
        orderQueue.addOrder(order);
        Tile tile = warehouse.findRackFor(item);
        order.setDestination(tile);
        if (tile == null) {
            // Can't insert this item!
            return null;
        } else {
            tile.getStorageUnit().addItem(item);
            orderQueue.completeOrder();
            return tile;
        }
    }

    /**
     * Returns a set of available TileNodes for the pathfinder
     * @return the TileNodes
     */
    public ArrayList<Tile> getNodes()
    {
        ArrayList<Tile> nodes = new ArrayList<>();

        Tile[][] tiles = this.getWarehouse().getTiles();
        for(int i = 0; i < tiles.length; i ++)
        {
            for (int j = 0; j < tiles[i].length; j++)
            {
                if (tiles[i][j].isEmpty())
                    nodes.add(tiles[i][j]);
            }
        }

        return nodes;
    }

    public ArrayList<Tile> getConnections(Tile t) throws TileOutOfBoundsException {
        ArrayList<Tile> connections = new ArrayList<>();

        if(this.getWarehouse().getTileAt(t.getX(), t.getY()+1).isEmpty())
            connections.add(this.getWarehouse().getTileAt(t.getX(), t.getY()+1));
        if(t.getY()-1 >= 0) {
            if (this.getWarehouse().getTileAt(t.getX(), t.getY() - 1).isEmpty())
                connections.add(this.getWarehouse().getTileAt(t.getX(), t.getY() - 1));
        }
        if(this.getWarehouse().getTileAt(t.getX()+1, t.getY()).isEmpty())
            connections.add(this.getWarehouse().getTileAt(t.getX()+1, t.getY()));
        if(t.getX()-1 >=0) {
            if (this.getWarehouse().getTileAt(t.getX() - 1, t.getY()).isEmpty())
                connections.add(this.getWarehouse().getTileAt(t.getX() - 1, t.getY()));
        }
        return connections;
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