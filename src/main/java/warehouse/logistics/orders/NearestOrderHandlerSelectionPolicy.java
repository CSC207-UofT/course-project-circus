package warehouse.logistics.orders;

import warehouse.Warehouse;
import warehouse.geometry.WarehouseCoordinateSystem;
import warehouse.robots.Robot;
import warehouse.robots.RobotMapper;
import warehouse.tiles.Tile;

import java.util.List;

/**
 * An order handler selection policy that finds the nearest Robot to the first waypoint of the NavigateOrder.
 */
public class NearestOrderHandlerSelectionPolicy implements OrderHandlerSelectionPolicy<NavigateOrder> {
    private final Warehouse<?, ?> warehouse;

    /**
     * Construct a NearestOrderHandlerSelectionPolicy given a Warehouse.
     */
    public NearestOrderHandlerSelectionPolicy(Warehouse<?, ?> warehouse) {
        this.warehouse = warehouse;
    }

    /**
     * Find the Robot closest to the first waypoint of the given order.
     * @param robots The Robots to select from.
     * @param order The Order to find a handler for.
     * @return The selected robot.
     */
    @Override
    public Robot select(List<Robot> robots, NavigateOrder order) {
        List<Tile> waypoints = order.getWaypoints();
        if (waypoints.size() == 0) {
            return null;
        } else {
            WarehouseCoordinateSystem<?> coordinateSystem = warehouse.getState().getCoordinateSystem();
            int p1 = waypoints.get(0).getIndex();
            RobotMapper<?> robotMapper = warehouse.getState().getRobotMapper();

            double minimumDistance = Double.MAX_VALUE;
            Robot closestRobot = null;
            for (Robot robot : robots) {
                int p2 = robotMapper.getRobotTileIndex(robot);
                double distance = coordinateSystem.getDistance(p1, p2);
                if (distance < minimumDistance) {
                    minimumDistance = distance;
                    closestRobot = robot;
                }
            }
            return closestRobot;
        }
    }
}
