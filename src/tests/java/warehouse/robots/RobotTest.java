package warehouse.robots;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class RobotTest {

    @Test
    public void testRobotConstructor()
    {
        Robot robot1 = new Robot();
        Robot robot2 = new Robot("Artem");
        assertNotEquals("", robot1.getId());
        assertEquals("Artem", robot2.getId());

        robot1.setId("Liam");

        assertEquals("Liam", robot1.getId());
        assertNotEquals(robot1, robot2);

        robot1.setId("Artem");

        assertEquals(robot1, robot2);
        assertNotEquals(0, robot1.hashCode());
    }

    @Test
    public void testRobotSetId() {
        Robot robot = new Robot("Artem");
        assertEquals(robot.getId(), "Artem");
        robot.setId("Shon");
        assertEquals(robot.getId(), "Shon");
    }
}
