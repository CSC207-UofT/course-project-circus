package warehouse.robots;

import warehouse.WarehouseState;
import warehouse.geometry.WarehouseCoordinate;
import warehouse.geometry.WarehouseCoordinateSystem;

import java.util.HashMap;

/**
 * Contains all robot updaters and updates them accordingly.
 */
public class RobotAdapterUpdater<T extends WarehouseCoordinateSystem<U>, U extends WarehouseCoordinate> {
    private final RobotAdapterFactory<T, U> adapterFactory;
    private final WarehouseState<T, U> warehouseState;
    private final HashMap<Robot, RobotAdapter<T, U>> robotAdapters;

    /**
     * Construct an RobotAdapterUpdater.
     */
    public RobotAdapterUpdater(RobotAdapterFactory<T, U> adapterFactory,
                               WarehouseState<T, U> warehouseState) {
        this.adapterFactory = adapterFactory;
        this.warehouseState = warehouseState;
        this.robotAdapters = new HashMap<>();
    }

    public void update() {
        RobotMapper<U> robotMapper = warehouseState.getRobotMapper();
        for (Robot robot : robotMapper.getRobots()) {
            if (!robotAdapters.containsKey(robot)) {
                robotAdapters.put(robot, adapterFactory.makeRobotAdapter(robot, warehouseState));
            }
        }
        for (RobotAdapter<T, U> adapter : robotAdapters.values()) {
            adapter.update();
        }
    }

    /**
     * Return the adapter for the given Robot.
     * @param robot The Robot whose adapter to retrieve.
     * @return the RobotAdapter for the given Robot. If it doesn't have one, then this method will create a new one.
     */
    public RobotAdapter<T, U> getRobotAdapter(Robot robot) {
        RobotAdapter<T, U> adapter;
        if (!robotAdapters.containsKey(robot)) {
            adapter = adapterFactory.makeRobotAdapter(robot, warehouseState);
            robotAdapters.put(robot, adapter);
        } else {
            adapter = robotAdapters.get(robot);
        }
        return adapter;
    }
}
