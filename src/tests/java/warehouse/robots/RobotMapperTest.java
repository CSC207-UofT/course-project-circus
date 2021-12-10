package warehouse.robots;

import org.junit.jupiter.api.Test;
import warehouse.geometry.grid.GridWarehouseCoordinateSystem;
import warehouse.geometry.grid.Point;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RobotMapperTest {
    @Test
    public void testRobotMapper()
    {
        RobotMapper<Point> robotMapper = new RobotMapper<>(new GridWarehouseCoordinateSystem(10, 10));
        Robot robot = new Robot(null);
        Point point = new Point(0, 0);

        robotMapper.addRobotAt(robot, point);
        assertTrue(robotMapper.isRobotAt(point));

        robotMapper.removeRobot(robot);
        assertFalse(robotMapper.isRobotAt(point));

        robotMapper.addRobotAt(robot, point);
        robotMapper.removeRobotsAt(point);
        assertFalse(robotMapper.isRobotAt(point));

        robotMapper.addRobotAt(robot, point);
        List<Robot> robots = robotMapper.getRobotsAt(point);
        assertEquals(robots.size(), 1); // There is only a single robot at (0, 0)
        assertEquals(robots.get(0), robot); // robot is at (0, 0)
        assertEquals(robots, robotMapper.getRobots()); // all the robots are at (0, 0)
        assertEquals(point, robotMapper.getRobotPosition(robot));
    }
}
