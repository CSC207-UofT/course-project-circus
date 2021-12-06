package warehouse.robots;

import warehouse.tiles.Tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controls all the Robots in the warehouse and manages their positions.
 */
public class RobotMapper {
    private final Map<Robot, Tile> robotPositions;
    private final Map<Tile, List<Robot>> inverseRobotMap;

    /**
     * Construct a RobotMapper.
     */
    public RobotMapper() {
        robotPositions = new HashMap<>();
        inverseRobotMap = new HashMap<>();
    }

    /**
     * Add a Robot to the Warehouse at the given tile.
     * @remark If the given Robot is already in the controller, then this will overwrite that entry!
     * @param robot The Robot to add.
     */
    public void addRobotAt(Robot robot, Tile tile) {
        robotPositions.put(robot, tile);
        if (!inverseRobotMap.containsKey(tile)) {
            inverseRobotMap.put(tile, new ArrayList<>());
        }
        inverseRobotMap.get(tile).add(robot);
    }

    /**
     * Remove the given Robot.
     * @param robot the Robot to remove.
     */
    public void removeRobot(Robot robot) {
        if (!robotPositions.containsKey(robot)) return;

        Tile tile = robotPositions.get(robot);
        robotPositions.remove(robot);
        inverseRobotMap.get(tile).remove(robot);
    }

    /**
     * Remove all the Robots at the given Tile position.
     * @param tile The location of the Robots to remove.
     */
    public void removeRobotsAt(Tile tile) {
        List<Robot> robots = getRobotsAt(tile);
        for (Robot robot : robots) {
            removeRobot(robot);
        }
    }

    /**
     * Check whether the given Tile contains a Robot.
     * @param tile The Tile to check.
     * @return True if there exists a Robot at the given Tile, and False otherwise.
     */
    public boolean isRobotAt(Tile tile) {
        return inverseRobotMap.containsKey(tile) && inverseRobotMap.get(tile).size() > 0;
    }

    /**
     * Get the Robots at the given Tile position.
     * @param tile The location of the Robot to retrieve.
     * @return a list Robots at the given Tile.
     */
    public List<Robot> getRobotsAt(Tile tile) {
        return new ArrayList<>(inverseRobotMap.getOrDefault(tile, new ArrayList<>()));
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
     * @return The Tile the robot is on, or null if the robot is not in this RobotMapper.
     */
    public Tile getRobotPosition(Robot robot) {
        return robotPositions.getOrDefault(robot, null);
    }
}
