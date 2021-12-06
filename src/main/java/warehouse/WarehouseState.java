package warehouse;

import warehouse.inventory.PartCatalogue;
import warehouse.logistics.orders.OrderQueue;
import warehouse.robots.RobotMapper;

/**
 * Base state for all applications.
 */
public class WarehouseState {
    private final Warehouse warehouse;
    private final PartCatalogue partCatalogue;
    private final RobotMapper robotMapper;
    private final OrderQueue orderQueue;

    /**
     * Construct a WarehouseState.
     * @param warehouse The warehouse.
     * @param partCatalogue The part catalogue.
     * @param robotMapper the robot mapper.
     * @param orderQueue The order queue.
     */
    public WarehouseState(Warehouse warehouse,
                          PartCatalogue partCatalogue,
                          RobotMapper robotMapper,
                          OrderQueue orderQueue) {
        this.warehouse = warehouse;
        this.partCatalogue = partCatalogue;
        this.robotMapper = robotMapper;
        this.orderQueue = orderQueue;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public PartCatalogue getPartCatalogue() {
        return partCatalogue;
    }

    public RobotMapper getRobotMapper() {
        return robotMapper;
    }

    public OrderQueue getOrderQueue() {
        return orderQueue;
    }
}
