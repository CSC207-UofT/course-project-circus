package warehouse.robots;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.lwjgl.system.CallbackI;
import warehouse.tiles.TilePosition;

import java.util.ArrayList;

public class RobotsTests {

    @Test
    public void testRobot()
    {
        Robot r1 = new Robot();
        Robot r2 = new Robot("Artem");
        Assertions.assertEquals("Artem", r2.getId());
        Assertions.assertNotEquals("", r1.getId());
        r1.setId("Liam");
        Assertions.assertEquals("Liam", r1.getId());
        Assertions.assertFalse(r1.equals(r2));
        r1.setId("Artem");
        Assertions.assertTrue(r1.equals(r2));
        Assertions.assertNotEquals(0, r1.hashCode());
    }

    @Test
    public void testRobotMapper()
    {
        RobotMapper rm = new RobotMapper();
        TilePosition tp = new TilePosition(0, 0);
        Robot r1 = new Robot();
        rm.addRobotAt(r1, tp);
        Assertions.assertTrue(rm.isRobotAt(tp));
        rm.removeRobot(r1);
        Assertions.assertFalse(rm.isRobotAt(tp));
        rm.addRobotAt(r1, tp);
        rm.removeRobotsAt(tp);
        Assertions.assertFalse(rm.isRobotAt(tp));
        rm.addRobotAt(r1, tp);
        ArrayList<Robot> lst = (ArrayList<Robot>) rm.getRobotsAt(tp);
        Assertions.assertEquals(lst, rm.getRobots());
        Assertions.assertEquals(tp, rm.getRobotPosition(r1));
    }
}
