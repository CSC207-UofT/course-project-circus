package warehouse.robots;

import warehouse.geometry.WarehouseCoordinate;
import warehouse.geometry.WarehouseCoordinateSystem;
import warehouse.tiles.Tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controls all the Robots in the warehouse and manages their positions.
 */
public class RobotMapper<T extends WarehouseCoordinate> {
    private final WarehouseCoordinateSystem<T> coordinateSystem;
    private final Map<Robot, Integer> robotMap;
    private final Map<Integer, List<Robot>> inverseRobotMap;

    /**
     * Construct a RobotMapper.
     */
    public RobotMapper(WarehouseCoordinateSystem<T> coordinateSystem) {
        this.coordinateSystem = coordinateSystem;
        robotMap = new HashMap<>();
        inverseRobotMap = new HashMap<>();
    }

    /**
     * Add a Robot at the given index.
     * @param robot The Robot to add.
     */
    public void addRobotAt(Robot robot, int index) {
        robotMap.put(robot, index);
        if (!inverseRobotMap.containsKey(index)) {
            inverseRobotMap.put(index, new ArrayList<>());
        }
        inverseRobotMap.get(index).add(robot);
    }

    /**
     * Add a Robot at the given coordinate
     * @param robot The Robot to add.
     */
    public void addRobotAt(Robot robot, T position) {
        int index = coordinateSystem.projectCoordinateToIndex(position);
        addRobotAt(robot, index);
    }

    /**
     * Remove the given Robot.
     * @param robot the Robot to remove.
     */
    public void removeRobot(Robot robot) {
        if (!robotMap.containsKey(robot)) return;

        int index = robotMap.get(robot);
        robotMap.remove(robot);
        inverseRobotMap.get(index).remove(robot);
    }

    /**
     * Remove all the Robots at the given coordinate.
     * @param index the tile index of Robots to remove.
     */
    public void removeRobotsAt(int index) {
        List<Robot> robots = getRobotsAt(index);
        for (Robot robot : robots) {
            removeRobot(robot);
        }
    }

    /**
     * Remove all the Robots at the given coordinate.
     * @param position The location of the Robots to remove.
     */
    public void removeRobotsAt(T position) {
        int index = coordinateSystem.projectCoordinateToIndex(position);
        removeRobotsAt(index);
    }

    /**
     * Removes every Robot in this RobotMapper.
     */
    public void removeAllRobots() {
        for (Robot robot : robotMap.keySet()) {
            removeRobot(robot);;
        }
    }

    /**
     * Check whether the given tile index contains a Robot.
     * @param index The tile index to check
     * @return True if there exists a Robot at the given tile index, and False otherwise.
     */
    public boolean isRobotAt(int index) {
        return inverseRobotMap.containsKey(index) && inverseRobotMap.get(index).size() > 0;
    }

    /**
     * Check whether the given coordinate contains a Robot.
     * @param position The coordinate to check
     * @return True if there exists a Robot at the given position, and False otherwise.
     */
    public boolean isRobotAt(T position) {
        int index = coordinateSystem.projectCoordinateToIndex(position);
        return isRobotAt(index);
    }

    /**
     * Get the Robots at the given tile index.
     * @param index The tile index of the Robots to retrieve.
     * @return a list Robots at the given tile index.
     */
    public List<Robot> getRobotsAt(int index) {
        return new ArrayList<>(inverseRobotMap.getOrDefault(index, new ArrayList<>()));
    }

    /**
     * Get the Robots at the given coordinate.
     * @param position The location of the Robots to retrieve.
     * @return a list Robots at the given position.
     */
    public List<Robot> getRobotsAt(T position) {
        int index = coordinateSystem.projectCoordinateToIndex(position);
        return getRobotsAt(index);
    }

    /**
     * Return a list of Robots stored in this RobotMapper.
     */
    public List<Robot> getRobots() {
        return new ArrayList<>(robotMap.keySet());
    }

    /**
     * Get the tile index of a Robot.
     * @param robot The robot whose tile index to retrieve.
     * @return The tile index of the robot, or -1 if the robot is not in this RobotMapper.
     */
    public int getRobotTileIndex(Robot robot) {
        return robotMap.getOrDefault(robot, -1);
    }

    /**
     * Get the position of a Robot.
     * @param robot The robot whose position to retrieve.
     * @return The position of the robot, or null if the robot is not in this RobotMapper.
     */
    public T getRobotPosition(Robot robot) {
        return coordinateSystem.projectIndexToCoordinate(getRobotTileIndex(robot));
    }

    /**
     * Set the position of a Robot.
     * @param robot The robot whose position to set.
     * @param position The new position of the Robot.
     */
    public void setRobotPosition(Robot robot, T position) {
        if (!robotMap.containsKey(robot)) return;
        removeRobot(robot);
        addRobotAt(robot, position);
    }

    /**
     * Get the coordinate system.
     */
    public WarehouseCoordinateSystem<T> getCoordinateSystem() {
        return coordinateSystem;
    }
}
