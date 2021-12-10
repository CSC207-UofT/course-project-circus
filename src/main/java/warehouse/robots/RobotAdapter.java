package warehouse.robots;

import utils.Vector3;
import warehouse.WarehouseState;
import warehouse.geometry.WarehouseCoordinate;
import warehouse.geometry.WarehouseCoordinateSystem;

/**
 * An adapter that bridges a real-world robot and the Robot entity.
 */
public abstract class RobotAdapter<T extends WarehouseCoordinateSystem<U>, U extends WarehouseCoordinate> {
    protected final Robot robotModel;
    protected final WarehouseState<T, U> warehouseStateModel;

    /**
     * Construct a RobotAdapter.
     * @param robotModel The Robot model.
     * @param warehouseStateModel The Warehouse model.
     */
    public RobotAdapter(Robot robotModel, WarehouseState<T, U> warehouseStateModel) {
        this.robotModel = robotModel;
        this.warehouseStateModel = warehouseStateModel;
    }

    /**
     * Update the Robot.
     */
    public abstract void update();

    /**
     * Get the robot model.
     */
    public Robot getRobotModel() {
        return robotModel;
    }

}
