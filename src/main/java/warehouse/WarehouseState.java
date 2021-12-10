package warehouse;

import warehouse.geometry.WarehouseCoordinateSystem;
import warehouse.inventory.PartCatalogue;
import warehouse.logistics.orders.OrderQueue;
import warehouse.robots.RobotAdapterFactory;
import warehouse.robots.RobotAdapterUpdater;
import warehouse.robots.RobotMapper;
import warehouse.geometry.WarehouseCoordinate;

/**
 * State of the warehouse.
 */
public class WarehouseState<T extends WarehouseCoordinateSystem<U>, U extends WarehouseCoordinate> {
    private final T coordinateSystem;
    private final PartCatalogue partCatalogue;
    private final WarehouseLayout<U> warehouseLayout;
    private final RobotMapper<U> robotMapper;
    private final RobotAdapterUpdater<T, U> robotAdapterUpdater;
    private final OrderQueue orderQueue;

    /**
     * Construct a WarehouseState.
     * @param partCatalogue The part catalogue.
     * @param warehouseLayout The warehouseLayout.
     * @param robotMapper the robot mapper.
     * @param orderQueue The order queue.
     * @remark The warehouseLayout and robotMapper should be equal to the coordinateSystem argument.
     */
    public WarehouseState(PartCatalogue partCatalogue,
                          T coordinateSystem,
                          WarehouseLayout<U> warehouseLayout,
                          RobotMapper<U> robotMapper,
                          RobotAdapterFactory<T, U> robotAdapterFactory,
                          OrderQueue orderQueue) {
        this.partCatalogue = partCatalogue;
        this.coordinateSystem = coordinateSystem;
        this.warehouseLayout = warehouseLayout;
        this.robotMapper = robotMapper;
        if (robotAdapterFactory != null) {
            this.robotAdapterUpdater = new RobotAdapterUpdater<>(robotAdapterFactory, this);
        } else {
            this.robotAdapterUpdater = null;
        }
        this.orderQueue = orderQueue;
        // Verify coordinate systems match!
        assert warehouseLayout.getCoordinateSystem().equals(coordinateSystem);
        assert robotMapper.getCoordinateSystem().equals(coordinateSystem);
    }

    public PartCatalogue getPartCatalogue() {
        return partCatalogue;
    }

    public T getCoordinateSystem() {
        return coordinateSystem;
    }

    public WarehouseLayout<U> getLayout() {
        return warehouseLayout;
    }

    public RobotMapper<U> getRobotMapper() {
        return robotMapper;
    }

    public RobotAdapterUpdater<T, U> getRobotAdapterUpdater() {
        return robotAdapterUpdater;
    }

    public OrderQueue getOrderQueue() {
        return orderQueue;
    }
}
