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

    /**
     * Construct a RobotMapper.
     */
    public RobotMapper() {
        robotPositions = new HashMap<>();
    }

    /**
     * Add a Robot to the Warehouse at the given tile.
     * @remark If the given Robot is already in the controller, then this will overwrite that entry!
     * @param robot The Robot to add.
     */
    public void addRobotAt(Robot robot, Tile tile) {
        robotPositions.put(robot, tile);
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
