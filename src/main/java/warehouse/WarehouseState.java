package warehouse;

import warehouse.inventory.PartCatalogue;
import warehouse.robots.RobotMapper;

/**
 * Base state for all applications.
 */
public class WarehouseState {
    private final Warehouse warehouse;
    private final PartCatalogue partCatalogue;
    private final RobotMapper robotController;

    /**
     * Construct a WarehouseState.
     * @param warehouse The warehouse.
     * @param partCatalogue The part catalogue.
     */
    public WarehouseState(Warehouse warehouse, PartCatalogue partCatalogue, RobotMapper robotController) {
        this.warehouse = warehouse;
        this.partCatalogue = partCatalogue;
        this.robotController = robotController;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public PartCatalogue getPartCatalogue() {
        return partCatalogue;
    }

    public RobotMapper getRobotController() {
        return robotController;
    }
}
