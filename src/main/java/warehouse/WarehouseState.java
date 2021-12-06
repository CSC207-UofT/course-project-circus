package warehouse;

import warehouse.inventory.PartCatalogue;
import warehouse.robots.RobotMapper;

/**
 * Base state for all applications.
 */
public class WarehouseState {
    private final Warehouse warehouse;
    private final PartCatalogue partCatalogue;
    private final RobotMapper robotMapper;

    /**
     * Construct a WarehouseState.
     * @param warehouse The warehouse.
     * @param partCatalogue The part catalogue.
     */
    public WarehouseState(Warehouse warehouse, PartCatalogue partCatalogue, RobotMapper robotMapper) {
        this.warehouse = warehouse;
        this.partCatalogue = partCatalogue;
        this.robotMapper = robotMapper;
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
}
