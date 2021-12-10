package warehouse.robots;

import utils.RandomUtils;
import warehouse.logistics.optimization.DistanceTileScorer;
import warehouse.logistics.optimization.graph.TileNode;
import warehouse.logistics.optimization.routefinding.Routefinder;
import warehouse.logistics.optimization.routefinding.algorithms.AStarRoutefinder;
import warehouse.logistics.orders.Order;
import warehouse.tiles.Tile;

import java.util.Objects;

/**
 * An agent in the warehouse.
 */
public class Robot {
    private String id;
    private Routefinder<TileNode> routefinder;
    private Order order = null;

    /**
     * Construct a Robot with the given id and routefinder.
     * @param id The id of the Robot.
     * @param routefinder The routefinder.
     */
    public Robot(String id, Routefinder<TileNode> routefinder) {
        this.id = id;
        this.routefinder = routefinder;
    }

    /**
     * Construct a Robot with a random id and given routefinder.
     */
    public Robot (Routefinder<TileNode> routefinder) {
        this(RandomUtils.randomId(), routefinder);
    }

    /**
     * Get this Robot's id.
     */
    public String getId() {
        return id;
    }

    /**
     * Set this Robot's id.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get the routefinder of this Robot.
     */
    public Routefinder<TileNode> getRoutefinder() {
        return routefinder;
    }

    /**
     * Set the routefiner of this Robot.
     */
    public void setRoutefinder(Routefinder<TileNode> routefinder) {
        this.routefinder = routefinder;
    }

    /**
     * Get this Robot's current order.
     */
    public Order getOrder() {
        return order;
    }

    /**
     * Set this Robot's current order.
     */
    public void setOrder(Order order) {
        this.order = order;
    }

    /**
     * Return whether the Robot currently has an order.
     */
    public Boolean getIsBusy() {
        return this.order != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Robot robot = (Robot) o;
        return Objects.equals(id, robot.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
