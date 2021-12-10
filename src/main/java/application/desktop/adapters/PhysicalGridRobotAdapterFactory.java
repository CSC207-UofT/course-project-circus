package application.desktop.adapters;

import warehouse.WarehouseState;
import warehouse.geometry.grid.GridWarehouseCoordinateSystem;
import warehouse.geometry.grid.Point;
import warehouse.robots.Robot;
import warehouse.robots.RobotAdapter;
import warehouse.robots.RobotAdapterFactory;

public class PhysicalGridRobotAdapterFactory implements RobotAdapterFactory<GridWarehouseCoordinateSystem, Point> {
    @Override
    public RobotAdapter<GridWarehouseCoordinateSystem, Point> makeRobotAdapter(Robot robotModel,
                                                                               WarehouseState<GridWarehouseCoordinateSystem,
                                                                               Point> warehouseStateModel) {
        return new PhysicalGridRobot(robotModel, warehouseStateModel);
    }
}
