package warehouse.robots;

import warehouse.tiles.Tile;
import warehouse.tiles.TilePosition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controls all the Robots in the warehouse and manages their positions.
 */
public class RobotMapper {
    private final Map<Robot, TilePosition> robotPositions;
    private final Map<TilePosition, List<Robot>> inverseRobotMap;

    /**
     * Construct a RobotMapper.
     */
    public RobotMapper() {
        robotPositions = new HashMap<>();
        inverseRobotMap = new HashMap<>();
    }

    /**
     * Add a Robot to the Warehouse at the given TilePosition.
     * @remark If the given Robot is already in the controller, then this will overwrite that entry!
     * @param robot The Robot to add.
     */
    public void addRobotAt(Robot robot, TilePosition position) {
        robotPositions.put(robot, position);
        if (!inverseRobotMap.containsKey(position)) {
            inverseRobotMap.put(position, new ArrayList<>());
        }
        inverseRobotMap.get(position).add(robot);
    }

    /**
     * Remove the given Robot.
     * @param robot the Robot to remove.
     */
    public void removeRobot(Robot robot) {
        if (!robotPositions.containsKey(robot)) return;

        TilePosition position = robotPositions.get(robot);
        robotPositions.remove(robot);
        inverseRobotMap.get(position).remove(robot);
    }

    /**
     * Remove all the Robots at the given TilePosition.
     * @param position The location of the Robots to remove.
     */
    public void removeRobotsAt(TilePosition position) {
        List<Robot> robots = getRobotsAt(position);
        for (Robot robot : robots) {
            removeRobot(robot);
        }
    }

    /**
     * Check whether the given Tile contains a Robot.
     * @param position The TilePosition to check.
     * @return True if there exists a Robot at the given Tile, and False otherwise.
     */
    public boolean isRobotAt(TilePosition position) {
        return inverseRobotMap.containsKey(position) && inverseRobotMap.get(position).size() > 0;
    }

    /**
     * Get the Robots at the given Tile position.
     * @param position The location of the Robot to retrieve.
     * @return a list Robots at the given Tile.
     */
    public List<Robot> getRobotsAt(TilePosition position) {
        return new ArrayList<>(inverseRobotMap.getOrDefault(position, new ArrayList<>()));
    }

    /**
     * Get the Robots in the Warehouse.
     */
    public List<Robot> getRobots() {
        return new ArrayList<>(robotPositions.keySet());
    }

    /**
     * Get the position of a Robot.
     * @param robot The robot whose position to retrieve.
     * @return The TilePosition of the robot, or null if the robot is not in this RobotMapper.
     */
    public TilePosition getRobotPosition(Robot robot) {
        return robotPositions.getOrDefault(robot, null);
    }

    /**
     * Find any Robots in the warehouse that are not currently processing an order.
     * @return List of available robots in the warehouse.
     */
    public ArrayList<Robot> findAvailableRobots() {
        ArrayList<Robot> robots = new ArrayList<>();
        for (Robot robot : this.getRobots()) {
            if (!robot.getIsBusy()) {
                robots.add(robot);
            }
        }
        return robots;
    }

    /**
     * Return whether there are any available robots.
     * @return True if there are available robots, false if there are not.
     */
    public Boolean robotsAvailable() {
        for (Robot robot: this.getRobots()) {
            if (!robot.getIsBusy()) {
                return true;
            }
        }
        return false;
    }
}
