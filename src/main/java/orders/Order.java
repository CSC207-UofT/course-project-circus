package orders;

import inventory.Item;
import warehouse.Tile;

/**
 * An order in the Warehouse.
 */
public class Order {
    private String status;
    private final Item item;
    private Tile currentLocation;
    private Tile destination = null;

    //TODO: Update currentLocation parameter once Shipping + Recieving depots are implemented.
    /**
     * Construct a new Order with its progress and item.
     * @param item The item in this order.
     */
    public Order(Item item) {
        this.item = item;
        this.currentLocation = null;
        this.status = "Order in progress";
    }

    /**
     * Update Order status once it is complete.
     */
    public void updateStatus() {
        this.currentLocation = this.destination;
        this.status = "Order complete";
    }

    /**
     * Set item destination once Order reaches front of priority Queue.
     * @param destination Order destination Tile.
     */
    public void setDestination(Tile destination) {
        this.destination = destination;
    }

    public String getStatus() {
        return this.status;
    }

    public Tile getDestination() {
        return this.destination;
    }

    public Tile getCurrentLocation() {
        return this.currentLocation;
    }

    public Item getItem() {
        return this.item;
    }

    @Override
    public String toString() {
        return "Order{" +
                "item id=" + item.getId() +
                ", status=" + status + "}";
    }

}
