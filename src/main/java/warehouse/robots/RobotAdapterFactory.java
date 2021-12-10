package warehouse.robots;

import warehouse.WarehouseState;
import warehouse.geometry.WarehouseCoordinate;
import warehouse.geometry.WarehouseCoordinateSystem;

public interface RobotAdapterFactory<T extends WarehouseCoordinateSystem<U>, U extends WarehouseCoordinate> {
    /**
     * Make a robot adapter.
     */
    RobotAdapter<T, U> makeRobotAdapter(Robot robotModel, WarehouseState<T, U> warehouseStateModel);
}
