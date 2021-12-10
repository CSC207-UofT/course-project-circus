package warehouse.logistics.orders;

import warehouse.tiles.Tile;
import java.util.List;

/**
 * An Order that requires a Robot to navigate to a list of Tiles.
 */
public abstract class NavigateOrder extends Order {
    protected final List<Tile> waypoints;

    /**
     * Construct a NavigateOrder.
     * @param waypoints A list of Tiles specifying where the Robot must go, in order.
     */
    public NavigateOrder(List<Tile> waypoints) {
        this.waypoints = waypoints;
    }

    public List<Tile> getWaypoints() {
        return waypoints;
    }
}
